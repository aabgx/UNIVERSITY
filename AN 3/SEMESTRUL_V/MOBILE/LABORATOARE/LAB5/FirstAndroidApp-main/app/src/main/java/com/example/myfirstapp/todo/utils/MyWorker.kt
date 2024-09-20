package com.example.myfirstapp.todo.utils

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.myfirstapp.MyAppDatabase
import com.example.myfirstapp.MyFirstApplication
import com.example.myfirstapp.todo.data.ItemRepository
import com.example.myfirstapp.todo.data.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit.SECONDS

class MyWorker(
    context: Context,
    val workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val itemRepository = (applicationContext as MyFirstApplication).container.itemRepository

        val notSaved = itemRepository.getLocallySaved()
        Log.d("MyWorker", notSaved.toString())

        notSaved.forEach{ song ->
            if(song._id.length < 12){
                song._id = ""
                val res = itemRepository.save(song)  // am pus isSaved = true in repository
            }
            else{
                var res = itemRepository.update(song)
            }
        }
        // perform long running operation
//        var s = 0
//        for (i in 1..workerParams.inputData.getInt("to", 1)) {
//            if (isStopped) {
//                break
//            }
//            SECONDS.sleep(1)
//            Log.d("MyWorker", "progress: $i")
//            setProgressAsync(workDataOf("progress" to i))
//            s += i
//        }
        return Result.success()
    }
}