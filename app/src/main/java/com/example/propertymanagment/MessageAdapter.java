package com.example.propertymanagment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeoutException;

import de.hdodenhof.circleimageview.CircleImageView;

//adapter to get the messages from the db
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    private List<Messages> messagesList;

    public MessageAdapter(List<Messages> messagesList){

        this.messagesList = messagesList;
    }

    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.message_single_layout, viewGroup, false);

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder messageViewHolder, int i) {

        Messages messages = messagesList.get(i);
        messageViewHolder.messageText.setText(messages.getMessage());
//        messageViewHolder.mProfilePic.setImage

    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText;
        public CircleImageView mProfilePic;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.message_text_layout);
            mProfilePic = (CircleImageView) itemView.findViewById(R.id.message_profile_layout);
            }
    }


}
