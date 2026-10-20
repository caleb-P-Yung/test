#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 InSize;

in vec2 texCoord;
out vec4 fragColor;

float lum(vec2 offset) {
    vec3 c = texture(DiffuseSampler, texCoord + offset / InSize).rgb;
    return dot(c, vec3(0.299, 0.587, 0.114));
}

void main() {
    float tl = lum(vec2(-1.0,  1.0)); float t = lum(vec2(0.0,  1.0)); float tr = lum(vec2(1.0,  1.0));
    float l  = lum(vec2(-1.0,  0.0));                                  float r  = lum(vec2(1.0,  0.0));
    float bl = lum(vec2(-1.0, -1.0)); float b = lum(vec2(0.0, -1.0)); float br = lum(vec2(1.0, -1.0));

    float gx = (tr + 2.0 * r + br) - (tl + 2.0 * l + bl);
    float gy = (tl + 2.0 * t + tr) - (bl + 2.0 * b + br);

        float edge = smoothstep(0.02, 0.2, sqrt(gx * gx + gy * gy));
    fragColor = vec4(vec3(edge), 1.0);
    
}