package com.teamdev.hinhnen4k.listen;

import com.teamdev.hinhnen4k.model.IMG;
import com.teamdev.hinhnen4k.model.IMGSearch;

import java.util.List;

public interface Listen {

    void onSussce(List<IMG> imgs,int type);
    void onSussceSearch(List<IMGSearch> imgSearches);
    void onFaied(int type);
}
