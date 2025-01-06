package com.example.myyoutuberagbot;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class data_Channel_info {
    @SerializedName("Channels")
    private Map<String, ChannelInfo> channels;

    public Map<String, ChannelInfo> getChannels() {
        return channels;
    }

    public static class ChannelInfo {
        @SerializedName("transcripts_saved")
        private int transcriptsSaved;

        @SerializedName("last_updated_on")
        private String lastUpdatedOn;

        public int getTranscriptsSaved() {
            return transcriptsSaved;
        }

        public String getLastUpdatedOn() {
            return lastUpdatedOn;
        }
    }
}


