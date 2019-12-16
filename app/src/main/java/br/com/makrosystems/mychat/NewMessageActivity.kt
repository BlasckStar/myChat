package br.com.makrosystems.mychat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_new_message.*

class NewMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        supportActionBar!!.title = "New Message for"

        fetchUsers()
    }

    fun fetchUsers(){
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()
                p0.children.forEach{
                    val user =it.getValue(UserAccount::class.java)
                    adapter.add(UserItem())
                }
                recyclerview_newmessages.adapter = adapter
            }
        })
    }

}


class UserItem: Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }
    override fun bind(viewHolder: ViewHolder, position: Int) {

    }
}

class UserAccount(val uid: String, val username: String, val profileImageUrl: String ){
    constructor() : this("", "", "")
}


