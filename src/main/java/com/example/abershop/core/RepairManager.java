package com.example.abershop.core;

import java.util.ArrayList;
import java.util.List;

public class RepairManager {

    private List<Repair> repairs;
    private RepairCsvManager repairCsvManager;
    private static RepairManager repairManager;

    private RepairManager() {
        repairs = new ArrayList<>();
        repairCsvManager = new RepairCsvManager();
    }

    public static RepairManager getInstance() {
        if (repairManager == null) {
            synchronized (RepairManager.class) {
                if (repairManager == null) {
                    repairManager = new RepairManager();
                }
            }
        }
        return repairManager;
    }

    public List<Repair> getRepairs() {
        repairs.clear();
        repairs = repairCsvManager.retrieveRepairs();
        return repairs;
    }

    public boolean addRepair(Repair repair) {
        return repairCsvManager.saveRepairs(repair, true);
    }

    public boolean updateRepairs(List<Repair> repairs) {
        repairCsvManager.clear();
        boolean success = true;
        for (Repair repair : repairs) {
            success = repairCsvManager.saveRepairs(repair, true);
            if (!success) {
                break;
            }
        }
        return success;
    }

    public void updateRepair(Repair repair) {
        List<Repair> allRepairs = getRepairs();
        for (int i = 0; i < allRepairs.size(); i++) {
            if (allRepairs.get(i).getRepairId() == repair.getRepairId()) {
                allRepairs.set(i, repair);
                break;
            }
        }
        updateRepairs(allRepairs);
    }


    public boolean addRepairs(List<Repair> repairs) {
        boolean success = true;
        for (Repair repair : repairs) {
            success = repairCsvManager.saveRepairs(repair, true);
            if (!success) {
                break;
            }
        }
        return success;
    }
}