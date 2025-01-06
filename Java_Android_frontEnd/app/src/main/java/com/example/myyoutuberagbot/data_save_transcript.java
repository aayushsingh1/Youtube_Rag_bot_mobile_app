package com.example.myyoutuberagbot;

public class data_save_transcript {

    int New_videos_transcripts_saved ;
    int total_transcripts_now_present;
    int Video_transcript_already_present;

    String reason_for_unsucessful_action;

    int successful;

    public int getNew_videos_transcripts_saved() {
        return New_videos_transcripts_saved;
    }

    public int getTotal_transcripts_now_present() {
        return total_transcripts_now_present;
    }

    public int getVideo_transcript_already_present() {
        return Video_transcript_already_present;
    }

    public String getReason_for_unsucessful_action() {
        return reason_for_unsucessful_action;
    }

    public int get_isSuccessful() {
        return successful;
    }
}
