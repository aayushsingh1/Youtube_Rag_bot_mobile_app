from flask import Flask, jsonify , request 
import json
import os
import socket

from langchain_google_genai import GoogleGenerativeAI
from datetime import datetime


from RAG_functions import *

app = Flask(__name__)


# Replace with your own LLM API key

GOOGLE_API_KEY = ''


# Replace with your own youtube service API key
youtube_api_key = ''
llm = GoogleGenerativeAI(model="gemini-1.5-flash",api_key=GOOGLE_API_KEY)


### Folder paths to store all data
transcript_path = f'/home/aayush/Downloads/For_youtube_rag_project/transcripts/'
chunk_storage_path = f'/home/aayush/Downloads/For_youtube_rag_project/transcripts with chunks/'
embedding_storage_path =  f'/home/aayush/Downloads/For_youtube_rag_project/embeddings/'
main_path  =f'/home/aayush/Downloads/For_youtube_rag_project/'
transcript_info_path =  f'/home/aayush/Downloads/For_youtube_rag_project/transcript_update_history.json'


## to check connectivity status
@app.route('/status', methods=['GET'])
def status():
    return jsonify({"connected": 1}), 200

## to list details of channels whose transcripts are downloaded
@app.route('/extracted_channels', methods=['GET'])
def get_content():
    with open(transcript_info_path, 'r') as file:
        existing_data =  json.load(file)
        if "Channels" not in existing_data:
            print('json file empty')
            existing_data["Channels"] = {}


    return existing_data

## View list of videos in channel
@app.route('/view_transcript/<string:channel_name>', methods=['GET'])
def get_channel_transcript_names(channel_name):
    directory = f'/home/aayush/Downloads/For_youtube_rag_project/transcripts/{channel_name}'

    # List all entries in the specified directory 
    entries = os.listdir(directory) 

        
    # Filter out files, keeping only directories
    subfiles = [entry for entry in entries if os.path.isfile(os.path.join(directory, entry))]
    return jsonify(subfiles=subfiles)

## View individual video transcript
@app.route('/view_transcript/<string:channel_name>', methods=['POST'])
def get_channel_transcripts(channel_name):
    data = request.get_json()

    if 'video_name' in data:
        video_name = data['video_name']

        file_name = f'/home/aayush/Downloads/For_youtube_rag_project/transcripts/{channel_name}/{video_name}'

        contents = ''
    
        with open(file_name, 'r') as file:
            # Read the contents of the file
            contents = file.read()
            # Print the contents
            # print(contents)


    return jsonify(contents=contents)

''''''
## Ask question 
@app.route('/ask_question/<string:channel_name>', methods=['POST'])
def ask_question_from_channel(channel_name):
    # from RAG_functions import ask_question_function 

    data = request.get_json()

    if 'question' in data:
        question = data['question']
        answer = ask_question_function(question,channel_name,GOOGLE_API_KEY)



    return jsonify(answer=answer)


## Save transcript for youtube channel
@app.route('/save_transcript/<string:channel_name>', methods=['GET'])
def save_transcript(channel_name):
    # from RAG_functions import save_transcript_in_text_format 

    new_videos_saved , transcript_already_present , is_successful = save_transcript_in_text_format(channel_name, transcript_path,youtube_api_key)

    
    if is_successful == 1:
            
        result = {"New_videos_transcripts_saved" : new_videos_saved ,
                "Video_transcript_already_present": transcript_already_present,
                    "total_transcripts_now_present" : new_videos_saved+transcript_already_present,
                    "successful": 1,
                    "reason_for_unsucessful_action":"Nil"}
        
        print("Here 1")


        existing_data = {}
        with open(transcript_info_path, 'r') as file:
            existing_data = json.load(file)
            if "Channels" not in existing_data:
                print('json file empty')
                existing_data["Channels"] = {}

            existing_data["Channels"][channel_name] = {
                'transcripts_saved': new_videos_saved + transcript_already_present,
                'last_updated_on': datetime.now().strftime("%d-%b-%y")
            }

            with open(transcript_info_path, 'w') as file:
                json.dump(existing_data, file, indent=4)




        return jsonify(result)
    
    else:
        result = {"New_videos_transcripts_saved" : 0 ,
                "Video_transcript_already_present": 0,
                "total_transcripts_now_present" : 0,
                "successful":0,
                "reason_for_unsucessful_action":new_videos_saved}
        
        existing_data = {}

        print("Here 2")
        with open(transcript_info_path, 'r') as file:
            existing_data = json.load(file)
            if "Channels" not in existing_data:
                print('json file empty')
                existing_data["Channels"] = {}

            existing_data["Channels"][channel_name] = {
                'transcripts_saved': 0,
                'last_updated_on': datetime.now().strftime("%d-%b-%y")
            }

            with open(transcript_info_path, 'w') as file:
                json.dump(existing_data, file, indent=4)

        
        return jsonify(result)

    



## Chunk downloaded text transcripts , and embedd them 
@app.route('/chunk_transcript_and_embedding/<string:channel_name>', methods=['GET'])
def chunk_transcript_and_embedding(channel_name):
    # from RAG_functions import chunk_and_save_documents_with_overlap , convert_text_to_embedding
    chunks_saved , success_status_one , cause_of_error_one = chunk_and_save_documents_with_overlap(transcript_path,chunk_storage_path,channel_name)
    result = {}
    

    if success_status_one == 1:
        embedded_chunks_generated , embedded_chunks_existing_already , success_status_two , cause_of_error_two =  convert_text_to_embedding(chunk_storage_path,embedding_storage_path,channel_name,GOOGLE_API_KEY)
    
        
        if success_status_two == 1:
            

            result = {'chunks_created_from_transcripts':chunks_saved,
                'New_Embeddings_generated_from_chunks':embedded_chunks_generated,
                'Embeddings_already_saved_from_old_chunks':embedded_chunks_existing_already,
                'is_successful':1,
                'reason_for_unsuccessful_response':'Nil'}
            
        elif success_status_two == 0:
            result = {'chunks_created_from_transcripts':chunks_saved,
                'New_Embeddings_generated_from_chunks':0,
                'Embeddings_already_saved_from_old_chunks':0,
                'is_successful':0,
                'reason_for_unsuccessful_response':cause_of_error_two}
            
    else:
        result = {'chunks_created_from_transcripts':chunks_saved,
            'New_Embeddings_generated_from_chunks':0,
            'Embeddings_already_saved_from_old_chunks':0,
            'is_successful':0,
            'reason_for_unsuccessful_response':cause_of_error_one}
            


            
    return jsonify(result)

## Get local ip address to host the app
def get_ipv6_address():
    # Create a socket
    s = socket.socket(socket.AF_INET6, socket.SOCK_DGRAM)
    
    try:
        # Connect to an external address (Google's public DNS server)
        s.connect(("2001:4860:4860::8888", 80))
        # Get the local IPv6 address
        ipv6_address = s.getsockname()[0]
    finally:
        s.close()
    
    return ipv6_address
    
if __name__ == '__main__':
    ## Code to host app on local pc
    ipv6 = get_ipv6_address()
    print(f"Local IPv6 Address: {ipv6}")
    app.run(debug=True, port=5000, host=ipv6)
