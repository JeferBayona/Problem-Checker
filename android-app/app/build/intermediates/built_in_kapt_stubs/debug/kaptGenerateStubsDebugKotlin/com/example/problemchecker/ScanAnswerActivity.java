package com.example.problemchecker;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0003\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\u0007\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0014J\b\u0010\u0010\u001a\u00020\rH\u0002J\b\u0010\u0011\u001a\u00020\rH\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\rH\u0014J+\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016\u00a2\u0006\u0002\u0010\u001cR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/example/problemchecker/ScanAnswerActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "<init>", "()V", "viewFinder", "Landroidx/camera/view/PreviewView;", "imageCapture", "Landroidx/camera/core/ImageCapture;", "cameraExecutor", "Ljava/util/concurrent/ExecutorService;", "problemStatement", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "startCamera", "takePhoto", "allPermissionsGranted", "", "onDestroy", "onRequestPermissionsResult", "requestCode", "", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "Companion", "app"})
public final class ScanAnswerActivity extends androidx.appcompat.app.AppCompatActivity {
    private androidx.camera.view.PreviewView viewFinder;
    @org.jetbrains.annotations.Nullable()
    private androidx.camera.core.ImageCapture imageCapture;
    private java.util.concurrent.ExecutorService cameraExecutor;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String problemStatement = "";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "CameraX-App";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS";
    private static final int REQUEST_CODE_PERMISSIONS = 10;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String[] REQUIRED_PERMISSIONS = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.problemchecker.ScanAnswerActivity.Companion Companion = null;
    
    public ScanAnswerActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void startCamera() {
    }
    
    private final void takePhoto() {
    }
    
    private final boolean allPermissionsGranted() {
        return false;
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @java.lang.Override()
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull()
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull()
    int[] grantResults) {
    }
    
    @kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\nX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u000b\u00a8\u0006\f"}, d2 = {"Lcom/example/problemchecker/ScanAnswerActivity$Companion;", "", "<init>", "()V", "TAG", "", "FILENAME_FORMAT", "REQUEST_CODE_PERMISSIONS", "", "REQUIRED_PERMISSIONS", "", "[Ljava/lang/String;", "app"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}