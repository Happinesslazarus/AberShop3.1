package com.example.abershop.core;

import com.example.abershop.controllers.dashboard.DashboardController;

import java.time.LocalDate;
import java.util.List;

public class PrecentageCal {

    private List<Repair> repairs;
    private List<Repair> currentRepairs;
    private List<Repair> completedRepairs;
    private LocalDate today;
    private LocalDate yesterday;
    private DashboardController controller;

    public  PrecentageCal( DashboardController controller){
        this.controller = controller;

    }
    public void initilize(List<Repair> repairs){
        this.repairs = repairs;
        today = LocalDate.now();
        yesterday = today.minusDays(1);
        currentRepairs = Filters.progressFilter(repairs, Status.PROGRESS);
        completedRepairs = Filters.progressFilter(repairs,Status.COMPLETE);
    }
    public void currentRepairCalculate(){
        List<Repair> ydayCurrentRepairs = Filters.dateFilter(currentRepairs,yesterday);
        List<Repair> todayCurrentRepairs = Filters.dateFilter(currentRepairs,today);

        int yesterdayCount = ydayCurrentRepairs.size();
        int todayCount = todayCurrentRepairs.size();

        controller.currentDiffrence = todayCount - yesterdayCount;
        controller.currentTotal = currentRepairs.size();

    }
    public void complteRepairCalculate(){
        List<Repair> ydayCompleteRepairs = Filters.dateFilter(completedRepairs,yesterday);
        List<Repair> todayCompleteRepairs = Filters.dateFilter(completedRepairs,today);

        int yesterdayCount = ydayCompleteRepairs.size();
        int todayCount = todayCompleteRepairs.size();

        controller.completeDiffrence = todayCount - yesterdayCount;
        controller.completeTotal = completedRepairs.size();


    }
}
