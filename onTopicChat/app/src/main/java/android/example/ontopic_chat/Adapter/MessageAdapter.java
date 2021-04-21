package android.example.ontopic_chat.Adapter;

import android.content.Context;
import android.example.ontopic_chat.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import android.example.ontopic_chat.Model.Chat;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context mContext;
    private List<Chat> mChats;
    private String imageurl;

    FirebaseUser firebaseUser;

    public MessageAdapter (Context mContext, List<Chat> mChats, String imageUrl) {
        this.mContext = mContext;
        this.mChats = mChats;
        this.imageurl = imageurl;
    }

    //inflate the chat item shape to be displayed beneath message
    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MSG_TYPE_RIGHT) {
            view = LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.chat_item_right, parent, false);
        } else {
            view = LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.chat_item_left, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Chat chat = mChats.get(position);

        holder.show_message.setText(chat.getMessage());

        if (imageurl != null && imageurl.equals("default")) {
            holder.profileImage.setImageResource(R.mipmap.ic_launcher_round);
        } else {
            Glide.with(mContext).load(imageurl).into(holder.profileImage);
        }

        //checks for the last message sent
        //place 'seen' or 'delivered' as indication for messaged received by user
        if (position == mChats.size() - 1) {
            if (chat.isSeen()) {
                holder.text_seen.setText("Seen");
            } else {
                holder.text_seen.setText("Delivered");
            }
        } else {
            //if message is not seen or delivered it will not display 'seen' or 'delivered'.
            holder.text_seen.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;
        public ImageView profileImage;
        public TextView text_seen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = (itemView).findViewById(R.id.show_message);
            profileImage = (itemView).findViewById(R.id.profile_image);
            text_seen = (itemView).findViewById(R.id.text_seen);
        }
    }

    //retrieve the user data along with messages and receiver data from database
    @Override
    public int getItemViewType(int position) {
       //return super.getItemViewType(position);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChats.get(position).equals(firebaseUser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
