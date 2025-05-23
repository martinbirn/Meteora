package org.meteora.presentation.util.shader

const val clouds_sksl = """
uniform float2 iResolution;      // Viewport resolution (pixels)
uniform float  iTime;            // Shader running time (seconds)
uniform float  weatherType;      // Weather type: 0.0 = sunny, 0.5 = partly cloudy, 1.0 = cloudy
uniform float2 sunPosition;      // Sun position, default (0.8, 0.2)

// ======= CUSTOMIZABLE PARAMETERS =======
// You can change these values to customize the appearance of the clouds

// Basic cloud settings
const float cloudscale = 1.1;   // Cloud scale, larger value = fewer clouds
const float speed = 0.03;       // Cloud movement speed
const float clouddark = 0.5;    // Shading of the lower part of the clouds
const float cloudlight = 0.3;   // Lighting of the upper part of the clouds
const float cloudcover = 0.2;   // Base cloud coverage of the sky
const float cloudalpha = 8.0;   // Cloud opacity
const float skytint = 0.5;      // Sky tint intensity

// Sky colors
const vec3 skycolour1 = vec3(0.2, 0.4, 0.6);  // Sky color at the top
const vec3 skycolour2 = vec3(0.4, 0.7, 1.0);  // Sky color at the bottom

// Sun settings
const float sun_size = 0.07;     // Size of the sun disk
const float sun_glow = 3.0;      // Sun glow intensity

// ======= END OF CUSTOMIZABLE PARAMETERS =======

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

// Optimized fbm function with early exit capability
float fbm(vec2 n) {
  float total = 0.0, amplitude = 0.1;
  // Number of iterations depends on weather (fewer for performance)
  int iterations = 7;
  if (weatherType > 0.0) {  // If weatherType variable is defined
    iterations = int(7.0 - weatherType * 2.0);
    iterations = max(4, iterations); // Minimum 4 iterations
  }
  
  for (int i = 0; i < 7; i++) {
    if (i >= iterations) break;  // Early exit
    
    total += noise(n) * amplitude;
    n = m * n;
    amplitude *= 0.4;
    
    // Early exit if contribution is negligible
    if (amplitude < 0.001) break;
  }
  return total;
}

// Function to render the sun
vec3 renderSun(vec2 uv, vec2 sunPos, float weatherType) {
    // If variables are not defined, use default values
    if (length(sunPos) < 0.01) sunPos = vec2(0.8, 0.2);
    float sunVisibility = 1.0;
    
    if (weatherType > 0.0) {  // If weatherType variable is defined
        sunVisibility = 1.0 - min(1.0, weatherType * 1.5);
    }
    
    if (sunVisibility <= 0.0) return vec3(0.0);
    
    float dist = length(uv - sunPos);
    
    // Sun disk
    float sunDisk = smoothstep(sun_size, sun_size - 0.005, dist) * sunVisibility;
    
    // Glow around the sun
    float sunGlow = exp(-dist * sun_glow) * 0.7 * sunVisibility;
    
    // Sun rays (if time permits)
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
    
    // Adjust cloud cover based on weather type
    float weatherBasedCloudCover = cloudcover;
    if (weatherType > 0.0) {  // If weatherType variable is defined
        weatherBasedCloudCover += weatherType * 0.4; // More clouds for cloudy weather
    }
    
    //ridged noise shape - forms the main cloud structure
    float r = 0.0;
    uv *= cloudscale;
    uv -= q - time;
    float weight = 0.8;
    
    // Optimize iterations based on weather type
    int iterations = 8;
    if (weatherType > 0.0) {  // If weatherType variable is defined
        iterations = int(8.0 - weatherType * 2.0);
        iterations = max(5, iterations); // Minimum 5 iterations
    }
    
    for (int i=0; i<8; i++){
        if (i >= iterations) break;  // Early exit
        r += abs(weight*noise( uv ));
        uv = m*uv + time;
        weight *= 0.7;
        
        // Early exit if contribution is negligible
        if (weight < 0.01) break;
    }
    
    //noise shape - adds details to clouds
    float f = 0.0;
    uv = p*vec2(iResolution.x/iResolution.y,1.0);
    uv *= cloudscale;
    uv -= q - time;
    weight = 0.7;
    
    for (int i=0; i<8; i++){
        if (i >= iterations) break;  // Early exit
        f += weight*noise( uv );
        uv = m*uv + time;
        weight *= 0.6;
        
        // Early exit if contribution is negligible
        if (weight < 0.01) break;
    }
    
    f *= r + f;
    
    //noise colour - adds color variations within clouds
    float c = 0.0;
    time = iTime * speed * 2.0;
    uv = p*vec2(iResolution.x/iResolution.y,1.0);
    uv *= cloudscale*2.0;
    uv -= q - time;
    weight = 0.4;
    
    for (int i=0; i<7; i++){
        if (i >= iterations-1) break;  // Early exit
        c += weight*noise( uv );
        uv = m*uv + time;
        weight *= 0.6;
        
        // Early exit if contribution is negligible
        if (weight < 0.01) break;
    }
    
    //noise ridge colour - adds texture to cloud edges
    float c1 = 0.0;
    time = iTime * speed * 3.0;
    uv = p*vec2(iResolution.x/iResolution.y,1.0);
    uv *= cloudscale*3.0;
    uv -= q - time;
    weight = 0.4;
    
    for (int i=0; i<7; i++){
        if (i >= iterations-1) break;  // Early exit
        c1 += abs(weight*noise( uv ));
        uv = m*uv + time;
        weight *= 0.6;
        
        // Early exit if contribution is negligible
        if (weight < 0.01) break;
    }
    
    c += c1;
    
    // Base sky color
    vec3 skycolour = mix(skycolour2, skycolour1, p.y);
    
    // Darken sky for cloudy weather
    if (weatherType > 0.0) {  // If weatherType variable is defined
        skycolour = mix(skycolour, skycolour * 0.8, weatherType * 0.5);
    }
    
    // Cloud color
    vec3 cloudcolour = vec3(1.1, 1.1, 0.9) * clamp((clouddark + cloudlight*c), 0.0, 1.0);
    
    // Darker clouds for cloudy weather
    if (weatherType > 0.0) {  // If weatherType variable is defined
        cloudcolour = mix(cloudcolour, cloudcolour * 0.9, weatherType * 0.5);
    }
    
    // Add sun (if variables are defined)
    vec3 sunEffect = vec3(0.0);
    if (weatherType >= 0.0) {  // Use this code if weatherType variable is defined
        sunEffect = renderSun(uv, sunPosition, weatherType);
        skycolour += sunEffect;
    }
    
    // Final cloud coverage
    f = weatherBasedCloudCover + cloudalpha*f*r;
    
    // Final color with sky and cloud blending
    vec3 result = mix(skycolour, clamp(skytint * skycolour + cloudcolour, 0.0, 1.0), clamp(f + c, 0.0, 1.0));
    
    return vec4( result, 1.0 );
}
"""