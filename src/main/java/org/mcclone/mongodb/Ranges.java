package org.mcclone.mongodb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/16.
 */
public class Ranges {
    private static final long total = 10000000000L;
    private List<Range> rangeList = new ArrayList<>();

    private Ranges(int rangeNum) {
        long step = total / rangeNum;
        long startKey = 0;
        for (int i = 0; i < rangeNum; i++) {
            rangeList.add(new Range(startKey + 1, startKey + step));
            startKey += step;
        }
    }

    public static Ranges build(int rangeNum) {
        return new Ranges(rangeNum);
    }

    public List<Range> getRanges() {
        return rangeList;
    }

    public static class Range {
        private Long startKey;
        private Long endKey;

        private Range(Long startKey, Long endKey) {
            this.startKey = startKey;
            this.endKey = endKey;
        }

        public Long getStartKey() {
            return startKey;
        }

        public Long getEndKey() {
            return endKey;
        }

        @Override
        public String toString() {
            return "Range{" +
                    "startKey=" + startKey +
                    ", endKey=" + endKey +
                    '}';
        }
    }
}
