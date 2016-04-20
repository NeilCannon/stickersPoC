package org.fuzzyrobot.kpresent.application;

/**
 * Created by neil on 31/03/2016.
 */

import org.fuzzyrobot.kpresent.CommentingFragment;
import org.fuzzyrobot.kpresent.activity.MainActivity;
import org.fuzzyrobot.kpresent.adapter.StickerRecyclerAdapter;
import org.fuzzyrobot.kpresent.rx.StickerService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ActivityModule.class})
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
    void inject(StickerRecyclerAdapter mainActivity);
    void inject(CommentingFragment commentingFragment);
    void inject(StickerService stickerService);
}