#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_LIGHT_SHADER

uniform float fraction;

varying vec4 vertColor;
varying vec3 vertNormal;
varying vec3 vertLightDir;

void main() {  
  float intensity;
  vec4 color;
  
  intensity = dot(vertLightDir, vertNormal);

  color = vec4(intensity*150/255, intensity*225/255, intensity*255/255, 1);
  //color = vec4(vec3(intensity), intensity);
  
  gl_FragColor = color * vertColor;  
}