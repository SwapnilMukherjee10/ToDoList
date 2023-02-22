package com.mustang.todolist

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    lateinit var item : EditText
    lateinit var add : Button
    lateinit var listView : ListView

    var itemList = ArrayList<String>()

    var fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        item = findViewById(R.id.editText)
        add = findViewById(R.id.button)
        listView = findViewById(R.id.list)

        itemList = fileHelper.readData(this)

        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,itemList)

        listView.adapter = arrayAdapter

        //Add button
        add.setOnClickListener {

            val itemName : String = item.text.toString()

            //When the editText is empty
            if(itemName == "") {
                Toast.makeText(applicationContext,"Type Something",Toast.LENGTH_SHORT).show()
            } else {
                itemList.add(itemName)
                item.setText("")
                fileHelper.writeData(itemList,applicationContext)
                arrayAdapter.notifyDataSetChanged()
            }
        }

        //Dialog box to delete the items on the list
        listView.setOnItemClickListener { adapterView, view, position, l ->

            val alert = AlertDialog.Builder(this,R.style.MyDialogTheme)
            alert.setTitle("Delete")
            alert.setMessage("Delete?")
            alert.setCancelable(false)
            alert.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->

                dialogInterface.cancel()

            })

            alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->

                itemList.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileHelper.writeData(itemList,applicationContext)

            })

            alert.create().show()

        }

    }
}