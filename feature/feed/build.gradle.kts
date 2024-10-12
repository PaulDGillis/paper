plugins {
    alias(libs.plugins.nowinandroid.android.feature)
    alias(libs.plugins.nowinandroid.android.library.compose)
}

android {
    namespace = "com.pgillis.paper.feature.feed"
}

dependencies {
    api(projects.core.data)
    implementation(libs.androidx.compose.material3)
}