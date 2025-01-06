package com.example.myyoutuberagbot;

public class data_generate_chunks_and_embeddings {

    private int chunks_created_from_transcripts;
    private int New_Embeddings_generated_from_chunks;
    private int Embeddings_already_saved_from_old_chunks;
    private int is_successful;
    private String reason_for_unsuccessful_response;

    public int getChunks_created_from_transcripts() {
        return chunks_created_from_transcripts;
    }

    public int getNew_Embeddings_generated_from_chunks() {
        return New_Embeddings_generated_from_chunks;
    }

    public int getEmbeddings_already_saved_from_old_chunks() {
        return Embeddings_already_saved_from_old_chunks;
    }

    public int getIs_successful() {
        return is_successful;
    }

    public String getReason_for_unsuccessful_response() {
        return reason_for_unsuccessful_response;
    }
}
