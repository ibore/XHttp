package me.ibore.http;

import java.util.ArrayList;
import java.util.List;

public class CancelerManager {

    private final List<CancelEntity> mRequestList;

    public CancelerManager() {
        this.mRequestList = new ArrayList<>();
    }

    /**
     * Add a task to cancel.
     *
     * @param request   target request.
     * @param canceller canceller.
     */
    public void addCancel(Request request, Canceller canceller) {
        CancelEntity cancelTag = new CancelEntity(request, canceller);
        mRequestList.add(cancelTag);
    }

    /**
     * Remove a task.
     *
     * @param request target request.
     */
    public void removeCancel(Request request) {
        CancelEntity cancelEntity = null;
        for (CancelEntity entity : mRequestList) {
            Request newRequest = entity.mRequest;
            if (request == newRequest) {
                cancelEntity = entity;
                break;
            }
        }
        if (cancelEntity != null) mRequestList.remove(cancelEntity);
    }

    /**
     * According to the tag to cancel a task.
     *
     * @param tag tag.
     */
    public void cancel(Object tag) {
        for (CancelEntity entity : mRequestList) {
            Object newTag = entity.mRequest.tag();
            if (tag == newTag || (tag != null && newTag != null && tag.equals(newTag))) {
                entity.mCanceller.cancel();
            }
        }
    }

    private static class CancelEntity {
        private final Request mRequest;
        private final Canceller mCanceller;

        private CancelEntity(Request request, Canceller canceller) {
            this.mRequest = request;
            this.mCanceller = canceller;
        }
    }

}
