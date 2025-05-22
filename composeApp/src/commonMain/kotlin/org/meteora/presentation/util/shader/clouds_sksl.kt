package org.meteora.presentation.util.shader

const val clouds_sksl = """
uniform float2 iResolution;      // Разрешение области просмотра (пиксели)
uniform float  iTime;            // Время выполнения шейдера (секунды)
uniform float  weatherType;      // Тип погоды: 0.0 = солнечно, 0.5 = переменная облачность, 1.0 = облачно
uniform float2 sunPosition;      // Позиция солнца, по умолчанию (0.8, 0.2)

// ======= НАСТРАИВАЕМЫЕ ПАРАМЕТРЫ =======
// Можно изменять эти значения для настройки внешнего вида облаков

// Основные настройки облаков
const float cloudscale = 1.1;   // Масштаб облаков, большее значение = меньше облаков
const float speed = 0.03;       // Скорость движения облаков
const float clouddark = 0.5;    // Затенение нижней части облаков
const float cloudlight = 0.3;   // Освещение верхней части облаков
const float cloudcover = 0.2;   // Базовое покрытие небосвода облаками
const float cloudalpha = 8.0;   // Непрозрачность облаков
const float skytint = 0.5;      // Интенсивность оттенка неба

// Цвета неба
const vec3 skycolour1 = vec3(0.2, 0.4, 0.6);  // Цвет неба сверху
const vec3 skycolour2 = vec3(0.4, 0.7, 1.0);  // Цвет неба снизу

// Настройки солнца
const float sun_size = 0.07;     // Размер солнечного диска
const float sun_glow = 3.0;      // Интенсивность свечения солнца

// ======= КОНЕЦ НАСТРАИВАЕМЫХ ПАРАМЕТРОВ =======

const mat2 m = mat2( 1.6,  1.2, -1.2,  1.6 );

vec2 hash( vec2 p ) {
  p = vec2(dot(p,vec2(127.1,311.7)), dot(p,vec2(269.5,183.3)));
  return -1.0 + 2.0*fract(sin(p)*43758.5453123);
}

float noise( in vec2 p ) {
    const float K1 = 0.366025404; // (sqrt(3)-1)/2;
    const float K2 = 0.211324865; // (3-sqrt(3))/6;
  vec2 i = floor(p + (p.x+p.y)*K1);  
    vec2 a = p - i + (i.x+i.y)*K2;
    vec2 o = (a.x>a.y) ? vec2(1.0,0.0) : vec2(0.0,1.0); //vec2 of = 0.5 + 0.5*vec2(sign(a.x-a.y), sign(a.y-a.x));
    vec2 b = a - o + K2;
  vec2 c = a - 1.0 + 2.0*K2;
    vec3 h = max(0.5-vec3(dot(a,a), dot(b,b), dot(c,c) ), 0.0 );
  vec3 n = h*h*h*h*vec3( dot(a,hash(i+0.0)), dot(b,hash(i+o)), dot(c,hash(i+1.0)));
    return dot(n, vec3(70.0));  
}

// Оптимизированная функция fbm с возможностью раннего завершения
float fbm(vec2 n) {
  float total = 0.0, amplitude = 0.1;
  // Количество итераций зависит от погоды (меньше для производительности)
  int iterations = 7;
  if (weatherType > 0.0) {  // Если определена переменная weatherType
    iterations = int(7.0 - weatherType * 2.0);
    iterations = max(4, iterations); // Минимум 4 итерации
  }
  
  for (int i = 0; i < 7; i++) {
    if (i >= iterations) break;  // Раннее завершение
    
    total += noise(n) * amplitude;
    n = m * n;
    amplitude *= 0.4;
    
    // Раннее завершение, если вклад незначителен
    if (amplitude < 0.001) break;
  }
  return total;
}

// Функция для рендеринга солнца
vec3 renderSun(vec2 uv, vec2 sunPos, float weatherType) {
    // Если переменные не определены, используем значения по умолчанию
    if (length(sunPos) < 0.01) sunPos = vec2(0.8, 0.2);
    float sunVisibility = 1.0;
    
    if (weatherType > 0.0) {  // Если определена переменная weatherType
        sunVisibility = 1.0 - min(1.0, weatherType * 1.5);
    }
    
    if (sunVisibility <= 0.0) return vec3(0.0);
    
    float dist = length(uv - sunPos);
    
    // Солнечный диск
    float sunDisk = smoothstep(sun_size, sun_size - 0.005, dist) * sunVisibility;
    
    // Сияние вокруг солнца
    float sunGlow = exp(-dist * sun_glow) * 0.7 * sunVisibility;
    
    // Лучи солнца (если есть время)
    float rays = 0.0;
    if (iTime > 0.0) {
        float rayAngle = atan(uv.y - sunPos.y, uv.x - sunPos.x);
        for (int i = 0; i < 5; i++) {
            float r = float(i) * 3.141592 * 2.0 / 5.0;
            rays += smoothstep(0.3, 0.0, abs(sin(rayAngle * 3.0 + r + iTime * 0.2)));
        }
        
        rays *= smoothstep(0.3, 0.0, dist) * smoothstep(0.5, 0.2, dist) * sunVisibility * 0.5;
    }
    
    vec3 sunColor = mix(vec3(1.0, 0.9, 0.6), vec3(1.0, 0.6, 0.3), 0.5);
    return sunColor * (sunDisk + sunGlow + rays);
}

// -----------------------------------------------

half4 main(in vec2 fragCoord ) {
    vec2 p = fragCoord.xy / iResolution.xy;
    vec2 uv = p*vec2(iResolution.x/iResolution.y,1.0);    
    float time = iTime * speed;
    float q = fbm(uv * cloudscale * 0.5);
    
    // Настройка покрытия облаками в зависимости от типа погоды
    float weatherBasedCloudCover = cloudcover;
    if (weatherType > 0.0) {  // Если определена переменная weatherType
        weatherBasedCloudCover += weatherType * 0.4; // Больше облаков для облачной погоды
    }
    
    //ridged noise shape - формирует основную структуру облаков
    float r = 0.0;
    uv *= cloudscale;
    uv -= q - time;
    float weight = 0.8;
    
    // Оптимизация итераций на основе типа погоды
    int iterations = 8;
    if (weatherType > 0.0) {  // Если определена переменная weatherType
        iterations = int(8.0 - weatherType * 2.0);
        iterations = max(5, iterations); // Минимум 5 итераций
    }
    
    for (int i=0; i<8; i++){
        if (i >= iterations) break;  // Раннее завершение
        r += abs(weight*noise( uv ));
        uv = m*uv + time;
        weight *= 0.7;
        
        // Раннее завершение, если вклад незначителен
        if (weight < 0.01) break;
    }
    
    //noise shape - добавляет детали к облакам
    float f = 0.0;
    uv = p*vec2(iResolution.x/iResolution.y,1.0);
    uv *= cloudscale;
    uv -= q - time;
    weight = 0.7;
    
    for (int i=0; i<8; i++){
        if (i >= iterations) break;  // Раннее завершение
        f += weight*noise( uv );
        uv = m*uv + time;
        weight *= 0.6;
        
        // Раннее завершение, если вклад незначителен
        if (weight < 0.01) break;
    }
    
    f *= r + f;
    
    //noise colour - добавляет вариации цвета внутри облаков
    float c = 0.0;
    time = iTime * speed * 2.0;
    uv = p*vec2(iResolution.x/iResolution.y,1.0);
    uv *= cloudscale*2.0;
    uv -= q - time;
    weight = 0.4;
    
    for (int i=0; i<7; i++){
        if (i >= iterations-1) break;  // Раннее завершение
        c += weight*noise( uv );
        uv = m*uv + time;
        weight *= 0.6;
        
        // Раннее завершение, если вклад незначителен
        if (weight < 0.01) break;
    }
    
    //noise ridge colour - добавляет текстуру к краям облаков
    float c1 = 0.0;
    time = iTime * speed * 3.0;
    uv = p*vec2(iResolution.x/iResolution.y,1.0);
    uv *= cloudscale*3.0;
    uv -= q - time;
    weight = 0.4;
    
    for (int i=0; i<7; i++){
        if (i >= iterations-1) break;  // Раннее завершение
        c1 += abs(weight*noise( uv ));
        uv = m*uv + time;
        weight *= 0.6;
        
        // Раннее завершение, если вклад незначителен
        if (weight < 0.01) break;
    }
    
    c += c1;
    
    // Основной цвет неба
    vec3 skycolour = mix(skycolour2, skycolour1, p.y);
    
    // Затемнение неба для облачной погоды
    if (weatherType > 0.0) {  // Если определена переменная weatherType
        skycolour = mix(skycolour, skycolour * 0.8, weatherType * 0.5);
    }
    
    // Цвет облаков
    vec3 cloudcolour = vec3(1.1, 1.1, 0.9) * clamp((clouddark + cloudlight*c), 0.0, 1.0);
    
    // Темнее облака для облачной погоды
    if (weatherType > 0.0) {  // Если определена переменная weatherType
        cloudcolour = mix(cloudcolour, cloudcolour * 0.9, weatherType * 0.5);
    }
    
    // Добавляем солнце (если определены переменные)
    vec3 sunEffect = vec3(0.0);
    if (weatherType >= 0.0) {  // Используем этот код, если определена переменная weatherType
        sunEffect = renderSun(uv, sunPosition, weatherType);
        skycolour += sunEffect;
    }
    
    // Итоговое покрытие облаками
    f = weatherBasedCloudCover + cloudalpha*f*r;
    
    // Итоговый цвет с учетом смешивания неба и облаков
    vec3 result = mix(skycolour, clamp(skytint * skycolour + cloudcolour, 0.0, 1.0), clamp(f + c, 0.0, 1.0));
    
    return vec4( result, 1.0 );
}
"""