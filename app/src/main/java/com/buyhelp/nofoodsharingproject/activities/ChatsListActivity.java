package com.buyhelp.nofoodsharingproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.buyhelp.nofoodsharingproject.ApplicationCore;
import com.buyhelp.nofoodsharingproject.R;
import com.buyhelp.nofoodsharingproject.databinding.ActivityListChatsBinding;
import com.buyhelp.nofoodsharingproject.models.Chat;
import com.buyhelp.nofoodsharingproject.utils.DefineUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
public class ChatsListActivity extends AppCompatActivity {
    private ActivityListChatsBinding binding;
    private DefineUser defineUser;
    private ArrayAdapter<String> arrayAdapter;
    private List<Chat> chats;
    private List<String> shortChats = new ArrayList<>();
    private Socket mSocket;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListChatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        defineUser = new DefineUser<>(this);

        ApplicationCore app = (ApplicationCore) getApplication();
        mSocket = app.getSocket();
        mSocket.connect();

        getChatsList();

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.item_chat, shortChats);
        binding.chatList.setAdapter(arrayAdapter);
        binding.chatList.setOnItemClickListener((parent, view, position, id) -> {
            Chat chat = chats.get(position);

            Intent intent = new Intent(ChatsListActivity.this, ChatActivity.class);
            intent.putExtra("chatID", chat.getChatID());
            startActivity(intent);
        });
        binding.chatsReturn.setOnClickListener(View -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }

    private void getChatsList() {
        Pair<String, Boolean> userData = defineUser.getTypeUser();
        String type = userData.second ? "getter" : "setter";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", type);
            jsonObject.put("userID", userData.first);
            mSocket.emit("send_user_id_to_get_chat", jsonObject);
        } catch (JSONException err) {
            err.printStackTrace();
        }

        mSocket.on("get_chats", args -> {
            runOnUiThread(() -> {
                JSONObject data = (JSONObject) args[0];
                chatsUpdate(data);
            });
        });
    }

    private void chatsUpdate(JSONObject data) {
        try {
            JSONArray chatsJSON = data.getJSONArray("result");

            chats = new ArrayList<>(chatsJSON.length());
            shortChats = new ArrayList<>(chatsJSON.length());

            for (int i = 0; i < chatsJSON.length(); i++) {
                JSONObject chat = chatsJSON.getJSONObject(i);
                Chat normalChat = new Chat();

                normalChat.setChatID(chat.getString("_id"));
                normalChat.setDateCreated(chat.getString("dateCreated"));
                normalChat.setTitle(chat.getString("title"));

                shortChats.add(chat.getString("title"));

                JSONArray users = chat.getJSONArray("users");
                String[] normalUsers = new String[users.length()];
                for (int j = 0; j < users.length(); j++) normalUsers[j] = users.getString(j);
                normalChat.setUsers(normalUsers);

                chats.add(normalChat);
            }

            arrayAdapter.clear();
            arrayAdapter.addAll(shortChats);
            arrayAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}