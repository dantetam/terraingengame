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
  
  intensity = abs(dot(vertLightDir, vertNormal));

  //color = vec4(1,1,1,1);
  color = vec4(intensity*150/255, intensity*225/255, intensity*255/255, 1);
  //color = vec4(vec3(intensity), intensity);
  
  //color = vec4(vec3(pow(intensity, fraction)), 1);
  
  gl_FragColor = color * vertColor;  
}