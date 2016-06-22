/*
 * Copyright (C) 2012 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ocandroid.retrofit_examples;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public final class SimpleService {
  public static final String API_URL = "https://api.github.com";

  public interface GitHub {
    @GET("/users/{user}/repos")
    Call<ResponseBody> repos(@Path("user") String user);
  }

  public static void main(String... args) throws IOException {
    // Create a very simple REST adapter which points the GitHub API.
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(API_URL)
        .build();

    // Create an instance of our GitHub API interface.
    GitHub github = retrofit.create(GitHub.class);

    // Create a call instance for looking up user repos.
    Response<ResponseBody> response = github.repos("square").execute();
    if (response.isSuccessful()) {
      System.out.println(response.body().string());
    }
    System.out.println("This won't print until the request completes");
    /*
    github.repos("ivanph").enqueue(new Callback<ResponseBody>() {
      @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
          try {
            System.out.println(response.body().string());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
      @Override public void onFailure(Call<ResponseBody> call, Throwable t) {

      }
    });
    System.out.println("This prints before the request completes");
    */
  }
}
