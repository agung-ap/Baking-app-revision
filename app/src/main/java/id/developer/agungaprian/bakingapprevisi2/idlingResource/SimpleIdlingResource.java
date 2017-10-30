package id.developer.agungaprian.bakingapprevisi2.idlingResource;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by agungaprian on 30/10/17.
 */

public class SimpleIdlingResource implements IdlingResource {
    public volatile ResourceCallback resourceCallback;
    public AtomicBoolean isIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        resourceCallback = callback;
    }

    public void setIdleState(boolean isIdleNow) {
        this.isIdleNow.set(isIdleNow);
        if (isIdleNow && resourceCallback != null){
            resourceCallback.onTransitionToIdle();
        }
    }
}
