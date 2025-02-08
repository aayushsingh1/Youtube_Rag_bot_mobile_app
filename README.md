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
I am using free tier of Youtube data API and Gemini LLM , so it is can face issues due to token limit reached if extracting channel with huge video count  .



## Features

- **Transcript Retrieval**: Automatically fetches transcripts from YouTube videos.
- ** View Saved Transcripts
- **Text Processing**: Performs chunking of transcripts for better data management and analysis.
- **Embeddings**: Generates embeddings for the text chunks to facilitate semantic search and question answering.
- **RAG-based Questioning**: Allows users to ask questions focused on a particular channel related to the transcripts, leveraging the embeddings for accurate responses.

## Requirements

To run the backend application, you will need:

- Python 3.x
- Flask
- YouTube API (for fetching transcripts)
- Libraries for text processing and embeddings (e.g., `transformers`, `sentence-transformers`, `numpy`, etc.)
     The python libraries needed are saved in requirement.txt file


  ## Understanding the Java android app
     In this repository do to Java_Android_frontEnd/app/src/main path .
  From there on
   /res/layout contains the xml files for various views in the mobile application .
  /java/com/example contains all java files .

   The Java files are of these type
   ApiClient.java : this file is used to enter the details of backend client
  
   Apiservice_xxx : to create an API request to backend flask app , with various formats
  
   data_xxx : these files match the json format which is from the API request is received from flask app
  
   view_xxx : these are files that implement the core logic of the frontend app , handling api requests and various front end widgets
  
   MainActivity.java : It handles the first view which is loaded when app is started initially 
   
   
   
   I have used retrofit library in java to handle the API requests

  
   ## IMP : To add the IP address of backend server need to modify below files
   
   1. Java_Android_frontEnd/app/src/main/java/com/example/myyoutuberagbot/ApiClient.java line no. 23
   2. Java_Android_frontEnd/app/src/main/res/xml/network_security_config.xml  line no. 4


