package com.blog.service.impl;

import com.blog.dao.TagDao;
import com.blog.entity.Tag;
import com.blog.service.TagService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shoji
 */
@Service
public class TagServiceImpl implements TagService {

    @Resource
    TagDao tagDao;

    @Override
    public int saveTag(Tag tag) {
        return tagDao.saveTag(tag);
    }

    @Override
    public Tag getTag(Integer id) {
        return tagDao.getTag(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagDao.getTagByName(name);
    }

    @Override
    public List<Tag> getAllTag() {
        return tagDao.getAllTag();
    }

    @Override
    public List<Tag> getBlogTag() {
        return tagDao.getBlogTag();
    }

    /**
     * tagIdsの文字列からidを取得，idでtagコレクションを取得
     * @param text タグ名
     * @return タグリスト
     */
    @Override
    public List<Tag> getTagByString(String text) {
        List<Tag> tags = new ArrayList<>();
        List<Integer> nums = convertToList(text);
        for (Integer num : nums) {
            tags.add(tagDao.getTag(num));
        }
        return tags;
    }

    /**
     * フロントからのtagIds文字列をlistへ転換
     * @param ids id
     * @return idリスト
     */
    private List<Integer> convertToList(String ids) {
        List<Integer> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idArrays = ids.split(",");
            for (String idArray : idArrays) {
                list.add(Integer.valueOf(idArray));
            }
        }
        return list;
    }

    @Override
    public int updateTag(Tag tag) {
        return tagDao.updateTag(tag);
    }

    @Override
    public int deleteTag(Integer id) {
        return tagDao.deleteTag(id);
    }
}
