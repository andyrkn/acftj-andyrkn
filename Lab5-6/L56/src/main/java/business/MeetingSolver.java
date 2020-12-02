package business;

import domain.Meeting;

import java.util.List;

public class MeetingSolver {
    private boolean success = false;
    private int[] mStart;
    private List<Meeting> meetings;

    public MeetingSolver(List<Meeting> meetings) {
        this.meetings = meetings;
        mStart = new int[meetings.size()];
        for (int i = 0; i < meetings.size(); i++) {
            mStart[i] = -1;
        }
    }

    public Solution solve() {
        back();
        return new Solution(success, mStart);
    }

    public boolean validStep(int k) {
        for (int i = 0; i < k; i++) {
            if (overlap(i, k)) {
                return false;
            }
        }
        return true;
    }

    public boolean overlap(int i, int k) {
        Meeting meetinga = meetings.get(i);
        Meeting meetingb = meetings.get(k);

        if (mStart[k] + meetingb.Duration > 9) {
            return true;
        }

        if ((mStart[i] <= mStart[k] && mStart[i] + meetinga.Duration > mStart[k]) ||
                (mStart[k] <= mStart[i] && mStart[k] + meetingb.Duration > mStart[i])) {
            return meetingb.Attendees
                    .stream()
                    .anyMatch(b -> meetinga.Attendees.contains(b));
        }

        return false;
    }

    public void back() {
        int k = 0;
        while (k < meetings.size() + 1) {
            if (k == -1) {
                return;
            }
            if (k == meetings.size()) {
                success = true;
                return;
            } else {
                mStart[k]++;
                if (mStart[k] < 9) {
                    if (validStep(k)) {
                        k++;
                    }
                } else {
                    mStart[k] = -1;
                    k--;
                }
            }
        }
    }
}
