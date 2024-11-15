package com.example.memobackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class MemoController {
    @Autowired
    private MemoList memoList = new MemoList();

    // 從MemoList中獲取MemoItem
    // 之後會看要不要新增ID搜尋，透過ID尋找指定的MemoList
    @RequestMapping(value = "/get_memo_items")
    public Set<MemoItem> getMemoItemSetOfMemoList() {
        return memoList.getMemoItems();
    }

    @RequestMapping(value = "/get_memo_item/{id}")
    public MemoItem getMemoItemOfMemoList(@PathVariable Long id) {
        return memoList.getMemoItemById(id);
    }

    @PostMapping(value = "/add_memo_item")
    public void addMemo(@RequestParam("title") String title,
                        @RequestParam("year") String year,
                        @RequestParam("month") String month,
                        @RequestParam("day") String day,
                        @RequestParam("hour") String hour,
                        @RequestParam("minute") String minute,
                        @RequestParam("alertTimeSelection") String alertTimeSelection,
                        @RequestParam("description") String description) {
        String time = year + '/' + month + '/' + day + ' ' + hour + ':' + minute;
        Long id = memoList.generateId();
        memoList.addMemoItem(new MemoItem(title, time,
                alertTimeSelection, description, id));
    }

    // 刪除指定的MemoItem
    @DeleteMapping(value = "/delete_memo_item/{id}")
    public void deleteMemoItem(@PathVariable String id) {
        Long memoItemId = Long.parseLong(id);
        memoList.removeMemoItemById(memoItemId);
    }

    @PutMapping(value = "/edit_memo_item/{id}")
    public void editMemoItem(@PathVariable Long id,
                             @RequestParam("title") String title,
                             @RequestParam("year") String year,
                             @RequestParam("month") String month,
                             @RequestParam("day") String day,
                             @RequestParam("hour") String hour,
                             @RequestParam("minute") String minute,
                             @RequestParam("alertTimeSelection") String alertTimeSelection,
                             @RequestParam("description") String description) {
        String time = year + '/' + month + '/' + day + ' ' + hour + ':' + minute;
        memoList.editContentOfMemoItemById(title, time, alertTimeSelection, description, id);
    }

    @PostMapping("/click_label")
    public void handleLabelClick(@RequestBody Map<String, Object> payload) {
        long GetId = ((Number) payload.get("id")).longValue();
        List<String> labels = (List<String>) payload.get("labels");

        System.out.println("Received ID: " + GetId);
        System.out.println("Received labels: " + labels);

        memoList.addLabelOnMemoItem(GetId, labels);

        //return new ResponseEntity<>("Labels received", HttpStatus.OK);
    }

    @GetMapping("/get_memo_items_by_label")
    public ResponseEntity<Set<MemoItem>> getMemoItemsByLabel(@RequestParam String label) {
        Set<MemoItem> memoItems = new HashSet<>();
        Set<MemoItem> oldMemoItems = memoList.getMemoItems();
        for (MemoItem memoItem : oldMemoItems) {
            for (Label memoLabel : memoItem.getLabelSet()) {
                if (Objects.equals(memoLabel.getName(), label)) {
                    memoItems.add(memoItem);
                }
            }
        }
        return new ResponseEntity<>(memoItems, HttpStatus.OK);
    }
}
