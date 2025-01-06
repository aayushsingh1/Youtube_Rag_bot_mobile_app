# Youtube_Rag_bot_mobile_app

# YouTube Transcript Analysis and RAG-based Questioning

## Overview

This  application is designed to read transcripts from YouTube videos, save and process the text data through chunking and embeddings, and enable users to ask Retrieval-Augmented Generation (RAG) based questions related to the transcripts of specific YouTube channels. 

The application aims to provide insights and facilitate information retrieval from video content.

All the data needed and extracted is stored on my personal PC .

The mobile application is made using Java for android .

REST APIs are used for communication


## Status
Currently hosting this app on my PC , so this app will be available when my PC is active and online
I am using free tier of Youtube data API and Gemini LLM , so it is easy to face issues due to token limit reached



## Features

- **Transcript Retrieval**: Automatically fetches transcripts from YouTube videos.
- ** View Saved Transcripts
- **Text Processing**: Performs chunking of transcripts for better data management and analysis.
- **Embeddings**: Generates embeddings for the text chunks to facilitate semantic search and question answering.
- **RAG-based Questioning**: Allows users to ask questions related to the transcripts, leveraging the embeddings for accurate responses.

## Requirements

To run this application, you will need:

- Python 3.x
- Flask
- YouTube API (for fetching transcripts)
- Libraries for text processing and embeddings (e.g., `transformers`, `sentence-transformers`, `numpy`, etc.)

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/your-repository-name.git
   cd your-repository-name
