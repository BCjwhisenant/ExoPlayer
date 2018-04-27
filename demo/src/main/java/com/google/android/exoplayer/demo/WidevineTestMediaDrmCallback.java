/*
 * Copyright (C) 2014 The Android Open Source Project
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
package com.google.android.exoplayer.demo;

import android.annotation.TargetApi;
import android.text.TextUtils;
import com.google.android.exoplayer.drm.ExoMediaDrm.KeyRequest;
import com.google.android.exoplayer.drm.ExoMediaDrm.ProvisionRequest;
import com.google.android.exoplayer.drm.MediaDrmCallback;
import com.google.android.exoplayer.util.Util;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

/**
 * A {@link MediaDrmCallback} for Widevine test content.
 */
@TargetApi(18)
public class WidevineTestMediaDrmCallback implements MediaDrmCallback {

  private static final String WIDEVINE_GTS_DEFAULT_BASE_URI =
      "https://proxy.uat.widevine.com/proxy";
  private static final Map<String, String> REQUEST_PROPERTIES =
      Collections.singletonMap("Content-Type", "application/octet-stream");

  private final String defaultUri;

  public WidevineTestMediaDrmCallback(String contentId, String provider) {
    String params = "?video_id=" + contentId + "&provider=" + provider;
//    defaultUri = WIDEVINE_GTS_DEFAULT_BASE_URI + params;

    // Lightbox
//    defaultUri = "https://manifest.prod.boltdns.net/license/v1/cenc/widevine/5641866319001/6e4e7cdf-495d-46df-8a18-8c7cfd735483/956ce58a-8153-4390-93c7-ec936f2c2af2?fastly_token=NWIwODgyY2VfYzFlZDBlNjhkYTk3MGU3ZDk1ZjdkNGIyMzg1ZTQzNGMzYmJjNjg3YTQ4MDZkNGRhMDFjOTFjNDg4MDEwYmYwNA%3D%3D";

    // Brightcove
    defaultUri = "https://manifest.prod.boltdns.net/license/v1/cenc/widevine/3636334164001/3edc3382-3c49-464e-956b-94974c283c22/afff2217-e360-4638-80fb-6eee14e37907?fastly_token=NWIwODg2MjFfYThlNjY4MzdjMjUyMzJhYmUzYzY4MzU1NDM3NjAyNThiNzA5ZTc0ZmQ5MGRiYTg3M2M2YWExN2UyMjQ4Y2E3NQ%3D%3D";
  }

  @Override
  public byte[] executeProvisionRequest(UUID uuid, ProvisionRequest request) throws IOException {
    String url = request.getDefaultUrl() + "&signedRequest=" + new String(request.getData());
    return Util.executePost(url, null, null);
  }

  @Override
  public byte[] executeKeyRequest(UUID uuid, KeyRequest request) throws IOException {
    String url = request.getDefaultUrl();
    if (TextUtils.isEmpty(url)) {
      url = defaultUri;
    }
    return Util.executePost(url, request.getData(), REQUEST_PROPERTIES);
  }

}
