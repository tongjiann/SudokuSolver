package com.xiwang.bean;

import lombok.Data;

import java.util.*;

/**
 * @author xiwang
 * @apiNote
 * @since 2023-01-05 14:02
 */
@Data
public class LatGroup {
    private List<Lat> latList;
    private Set<Integer> exist = new HashSet<>();
    private Set<Integer> possible = new HashSet<>();
    private Map<Integer, Integer> possibleCnt = new HashMap<>();
    private Integer index;
    private boolean isStable = false;
    private String type;

    public LatGroup(String type, int index) {
        this.type = type;
        this.index = index;
        for (int i = 1; i < 10; i++) {
            this.possible.add(i);
            this.possibleCnt.put(i, 9);
        }
    }

    public void addLat(Lat lat) {
        if (latList == null) {
            latList = new ArrayList<>();
        }
        latList.add(lat);
    }

    public void onSetVal(int row, int col, int val) {
        if (isStable) {
            return;
        }
        boolean add = exist.add(val);
        if (!add) {
            throw new RuntimeException("正在设置一个非法的值");
        }
        if (exist.size() == 9) {
            isStable = true;
        }
        possible.remove(val);

        latList.forEach(lat -> lat.onSetVal(row, col, val));
    }

    public String getPossibleCntStr() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ ");
        for (int i = 1; i < 10; i++) {
            stringBuilder
                    .append(i)
                    .append(" = ");
            if (possibleCnt.containsKey(i)) {
                stringBuilder.append(possibleCnt.get(i));
            } else {
                stringBuilder.append("0");
            }
            stringBuilder.append(" , ");
        }
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    public Map<Integer, Integer> getPossibleCnt() {
        return possibleCnt;
    }
}
