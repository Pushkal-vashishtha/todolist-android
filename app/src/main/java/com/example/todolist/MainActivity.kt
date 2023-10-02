package com.example.todolist

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.todolist.R.*

class MainActivity : AppCompatActivity() {

    lateinit var item : EditText
    lateinit var add : Button
    lateinit var listView : ListView

    var itemList = ArrayList<String>()

    var fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        item = findViewById(R.id.editText)
        add = findViewById(R.id.button)
        listView = findViewById(R.id.listv)

        itemList = fileHelper.readData(this)

        var arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,itemList)

        listView.adapter = arrayAdapter

        add.setOnClickListener{
            var itemName : String = item.text.toString()
            itemList.add(itemName)
            item.setText(" ")
            fileHelper.writeData(itemList,applicationContext)
            arrayAdapter.notifyDataSetChanged()


        }
        listView.setOnItemClickListener{ adapterView, view, position, l->

            var alert = AlertDialog.Builder(this)
            alert.setTitle("DELETE")
            alert.setMessage("Are You Sure ? ")
            alert.setCancelable(false)
            alert.setNegativeButton("No",DialogInterface.OnClickListener{ dialogInteface, i->
                dialogInteface.cancel()
            })
            alert.setPositiveButton("Yes",{ dialogInterface, i->

                itemList.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileHelper.writeData(itemList,applicationContext)

            })
            alert.create().show()
        }

    }
}