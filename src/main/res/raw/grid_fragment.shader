#extension GL_OES_EGL_image_external : require
precision mediump float;
uniform samplerExternalOES v_Texture;
varying vec2 texCoord;

varying vec4 v_Color;
varying vec3 v_Grid;
varying float v_isFloor;

void main() {
    float depth = gl_FragCoord.z / gl_FragCoord.w; // calculate world-space distance

    if (v_isFloor > 0.5) {
        if ((mod(abs(v_Grid[0]), 10.0) < 0.1) || (mod(abs(v_Grid[2]), 10.0) < 0.1)) {
            gl_FragColor = max(0.0, (90.0-depth) / 90.0) * vec4(1.0, 1.0, 1.0, 1.0)
                    + min(1.0, depth / 90.0) * v_Color;
        } else {
            gl_FragColor = v_Color;
        }
    } else if(v_isFloor > 0.2) {
        gl_FragColor = texture2D(v_Texture, texCoord);
    } else {
        gl_FragColor = v_Color;
    }
}