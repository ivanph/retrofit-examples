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

import com.google.gson.annotations.SerializedName;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public final class SimpleServiceWithConverter {
  public static final String API_URL = "https://api.github.com";

  public static class Repo {
    public final String name;
    public final boolean fork;
    @SerializedName("stargazers_count")
    public final int stars;

    public Repo(String name, boolean fork, int stars) {
      this.name = name;
      this.fork = fork;
      this.stars = stars;
    }
  }

  public interface GitHub {
    @GET("/users/{user}/repos")
    Call<List<Repo>> repos(@Path("user") String user);
  }

  public static void main(String... args) throws IOException {
    // Create a very simple REST adapter which points the GitHub API.
    Retrofit retrofit = new Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(API_URL)
        .build();

    // Create an instance of our GitHub API interface.
    GitHub github = retrofit.create(GitHub.class);
    github.repos("ivanph").enqueue(new Callback<List<Repo>>() {
      @Override public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
        String forkMark = "";
        if (response.isSuccessful()) {
          List<Repo> repos = response.body();
          for (Repo repo : repos) {
            forkMark = repo.fork ? "*" : "";
            System.out.println(forkMark + repo.name + " (" + repo.stars + ")");
          }
        }
      }

      @Override public void onFailure(Call<List<Repo>> call, Throwable t) {
        t.printStackTrace();
      }
    });
  }
}
