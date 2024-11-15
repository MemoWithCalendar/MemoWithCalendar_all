package com.example.memobackend;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MemoList {
    private Set<MemoItem> memoItems;
    private Calendar calendar;

    public MemoList() {
        memoItems = new HashSet<>();
        calendar = new Calendar(this);
    }

    // 添加 memoItem 到集合
    public void addMemoItem(MemoItem memoItem) {
        memoItems.add(memoItem);
    }

    // 查找集合中是否包含某個 memoItem
    public boolean containsMemoItem(MemoItem memoItem) {
        return memoItems.contains(memoItem);
    }

    // 刪除集合中的某個 memoItem
    public void removeMemoItemById(Long id) {
        MemoItem memoItem = getMemoItemById(id);
        memoItems.remove(memoItem);
    }

    public void addLabelOnMemoItem(long id, List<String> labels) {
        MemoItem memoItem = findMemoItemById(id);
        for(String label : labels) {
            memoItem.addLabel(new Label(label));
        }
    }

    public MemoItem findMemoItemById(Long id) {
        for (MemoItem memoItem : memoItems) {
            if (memoItem.getId().equals(id)) {
                return memoItem;
            }
        }
        return null;
    }

    public Long generateId() {
        Long id;
        // 生成隨機數字
        Random random = new Random();
        do {
            id = random.nextLong() % 1000000; // 產生隨機數
        } while (id <= 0 || containsId(id)); // 檢查是否為0或負數，以及是否已經存在

        return id;
    }

    // 檢查集合中是否存在具有特定 id 的 MemoItem
    public boolean containsId(Long id) {
        for (MemoItem memoItem : memoItems) {
            if (memoItem.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void editContentOfMemoItemById(String title, String time, String alertTimeSelection,
                                            String description, Long id) {
        MemoItem memoItem = getMemoItemById(id);
        memoItem.editContent(title, time, alertTimeSelection, description);
    }

    public Set<MemoItem> getMemoItems() {
        return this.memoItems;
    }

    // 在集合中尋找具有特定 id 的 MemoItem
    public MemoItem getMemoItemById(Long id) {
        for (MemoItem memoItem : memoItems) {
            if (memoItem.getId().equals(id)) {
                return memoItem;
            }
        }
        return null;
    }
  
    // 取得所有的日期
    public List<Integer> getAllMemoItemParseDayFormatToInt(){
        List<Integer> days = new ArrayList<>();
        System.out.println("memoItems.size(): " + memoItems.size());
        for (MemoItem memoItem : memoItems) {
            days.add(memoItem.parseDayFormatToInt());
        }
        return days;
    }

    public Calendar getCalendar() {
        return calendar;
    }
}
