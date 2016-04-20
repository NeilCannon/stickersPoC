package org.fuzzyrobot.kpresent.application;

/**
 * Created by neil on 31/03/2016.
 */

import org.fuzzyrobot.kpresent.rx.StickerService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface ApplicationComponent {
//    void inject(MainActivity mainActivity);
//    void inject(StickerRecyclerAdapter mainActivity);
//    void inject(CommentingFragment commentingFragment);
    void inject(StickerService stickerService);

}