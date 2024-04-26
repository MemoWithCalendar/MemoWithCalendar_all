package com.example.memobackend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarController {
    private final Calendar calendar = new Calendar();

    @GetMapping("/calendar")
    public String getCalendar(@RequestParam int year, @RequestParam int month, @RequestParam int day) {
        return calendar.generateCalendarDays(year, month, day);
    }

}