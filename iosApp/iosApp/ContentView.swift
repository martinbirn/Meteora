import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    let component: DefaultAppComponent
    let backDispatcher: BackDispatcher

    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(component: component, backDispatcher: backDispatcher)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    let component: DefaultAppComponent
    let backDispatcher: BackDispatcher

    var body: some View {
        ComposeView(component: component, backDispatcher: backDispatcher)
            .ignoresSafeArea()
    }
}
