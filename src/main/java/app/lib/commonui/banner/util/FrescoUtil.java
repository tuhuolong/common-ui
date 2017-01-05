
package app.lib.commonui.banner.util;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by chenhao on 16/12/28.
 */

public class FrescoUtil {
    public static void loadImage(final SimpleDraweeView simpleDraweeView, final String imageUrl,
            ResizeOptions resizeOptions) {
        if (simpleDraweeView == null || TextUtils.isEmpty(imageUrl)) {
            return;
        }
        String cur = (String) simpleDraweeView.getTag(simpleDraweeView.getId());
        if (TextUtils.equals(imageUrl, cur)) {
            return;
        }
        ImageRequestBuilder requestBuilder = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(imageUrl))
                .setProgressiveRenderingEnabled(true);
        if (resizeOptions != null) {
            requestBuilder.setResizeOptions(resizeOptions);
        }

        ImageRequest request = requestBuilder.build();

        ControllerListener listener = new BaseControllerListener() {
            @Override
            public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
                simpleDraweeView.setTag(simpleDraweeView.getId(), imageUrl);
            }
        };
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(simpleDraweeView.getController())
                .setImageRequest(request)
                .setControllerListener(listener)
                .build();
        simpleDraweeView.setController(controller);
        simpleDraweeView.getHierarchy().setFadeDuration(100);
        simpleDraweeView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
    }
}
