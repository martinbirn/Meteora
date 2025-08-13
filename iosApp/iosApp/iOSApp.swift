import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate

    var body: some Scene {
        WindowGroup {
            ContentView(component: appDelegate.appComponent, backDispatcher: appDelegate.backDispatcher)
        }
    }
}

class AppDelegate : NSObject, UIApplicationDelegate, ObservableObject {
    let backDispatcher: BackDispatcher = BackDispatcherKt.BackDispatcher()
    private var applicationLifecycle: ApplicationLifecycle
    @Published var appComponent: DefaultAppComponent

    override init() {
        KoinKt.doInitKoin()

        applicationLifecycle = ApplicationLifecycle()
        appComponent = DefaultAppComponent(
            componentContext: DefaultComponentContext(
                lifecycle: applicationLifecycle,
                stateKeeper: nil,
                instanceKeeper: nil,
                backHandler: backDispatcher
            )
        )
    }
}
