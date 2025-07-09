# 3D Ray Tracing Engine

A comprehensive 3D ray tracing rendering engine implemented in Java, capable of generating photorealistic images with advanced lighting, shadows, reflections, and refractions.

## Authors
- **Jeshurun**
- **Binyamin**

## Project Overview

This project implements a complete ray tracing pipeline that can render complex 3D scenes with realistic lighting effects. The engine supports multiple geometric primitives, various light sources, and advanced rendering techniques like anti-aliasing and BVH optimization.

## Features

### Core Rendering
- **Ray Tracing Pipeline**: Complete implementation of ray-sphere, ray-triangle, ray-plane intersections
- **Camera System**: Configurable camera with adjustable position, direction, and view plane
- **Image Generation**: Outputs high-quality PNG images
- **Multi-threading Support**: Parallel rendering for improved performance

### Geometry Support
- **Primitive Shapes**: Spheres, triangles, planes, cylinders, tubes, polygons
- **Complex Geometries**: Support for composite geometries and scene hierarchies
- **OBJ File Loading**: Import 3D models from standard OBJ format files with MTL material support
- **BVH Optimization**: Bounding Volume Hierarchy for efficient ray-geometry intersection testing

### Lighting System
- **Ambient Light**: Global illumination base level
- **Directional Light**: Parallel light rays (e.g., sunlight)
- **Point Light**: Omnidirectional light source
- **Spot Light**: Focused cone of light with adjustable angle

### Advanced Effects
- **Shadows**: Realistic shadow casting with transparency support
- **Reflections**: Mirror-like reflections with configurable intensity
- **Refractions**: Light refraction through transparent materials
- **Anti-aliasing**: Smooth edges through supersampling techniques
- **Material Properties**: Diffuse, specular, and transparency coefficients

## Project Structure

```
ISE5785_8831_5135/
├── src/
│   ├── geometries/          # Geometric primitives and intersection logic
│   ├── lighting/            # Light sources and illumination models
│   ├── primitives/          # Basic mathematical structures (Point, Vector, Color, etc.)
│   ├── renderer/            # Ray tracing engine and image generation
│   ├── scene/               # Scene management and composition
│   └── test/                # Main test class and utilities
├── unittests/               # Comprehensive unit tests
├── images/                  # Generated output images
└── doc/                     # Generated JavaDoc documentation
```

## Key Classes

### Core Engine
- `Camera`: Manages view transformation and ray generation
- `SimpleRayTracer`: Main ray tracing algorithm implementation
- `Scene`: Container for all scene objects and lighting
- `ImageWriter`: Handles image output and file generation

### Geometry
- `Sphere`, `Triangle`, `Plane`: Basic geometric primitives
- `Geometries`: Collection and management of scene objects
- `Intersectable`: Interface for ray-geometry intersection
- `BVHNode`: Bounding Volume Hierarchy optimization

### Lighting
- `AmbientLight`: Base illumination
- `DirectionalLight`, `PointLight`, `SpotLight`: Various light sources
- `Material`: Surface properties for realistic shading

### Utilities
- `Ray`: Mathematical ray representation
- `Point`, `Vector`: 3D coordinate system primitives
- `Color`: RGB color management
- `ObjReader`: 3D model file parser

## Getting Started

### Prerequisites
- Java 24 or higher
- JUnit 5 for running tests

### Building the Project
```bash
# Compile all source files
javac -cp "src:unittests" src/**/*.java unittests/**/*.java

# Or use the provided VS Code task
# Run: Terminal -> Run Task -> build cpp
```

### Running Tests
The project includes comprehensive unit tests that demonstrate various rendering capabilities:

```bash
# Run all tests
java -cp "src:unittests" org.junit.runner.JUnitCore renderer.RenderTests

# Run specific test categories
java -cp "src:unittests" org.junit.runner.JUnitCore renderer.LightsTests
java -cp "src:unittests" org.junit.runner.JUnitCore renderer.ReflectionRefractionTests
java -cp "src:unittests" org.junit.runner.JUnitCore renderer.ShadowTests
```

### Basic Usage Example

```java
import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;
import scene.Scene;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import geometries.Sphere;
import primitives.*;

// Create a scene
Scene scene = new Scene("My Scene")
    .setBackground(new Color(135, 206, 235))  // Sky blue
    .setAmbientLight(new AmbientLight(new Color(255, 255, 255)));

// Add objects to the scene
scene.geometries.add(
    new Sphere(50, new Point(0, 0, -100))
        .setEmission(new Color(255, 0, 0))
        .setMaterial(new Material().setKD(0.4).setKS(0.3).setShininess(100))
);

// Add lighting
scene.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));

// Create and configure camera
Camera camera = Camera.getBuilder()
    .setLocation(new Point(0, 0, 1000))
    .setDirection(Point.ZERO, Vector.AXIS_Y)
    .setVpDistance(1000)
    .setVpSize(200, 200)
    .setRayTracer(scene, RayTracerType.SIMPLE)
    .setResolution(500, 500)
    .build();

// Render the image
camera.renderImage().writeToImage("my_scene");
```

## Sample Renders

The project can generate various types of images:

- **Basic Shapes**: Spheres, triangles, and planes with different materials
- **Complex Lighting**: Multiple light sources with shadows and reflections
- **3D Models**: OBJ file imports like elephants, geometric shapes, and abstract art
- **Transparency Effects**: Glass-like materials with refractions
- **Anti-aliasing**: Smooth, high-quality renders

Sample images are generated in the `images/` directory when running the test suite.

## Performance Features

- **Multi-threading**: Parallel ray tracing for faster rendering
- **BVH Trees**: Spatial partitioning for efficient intersection testing
- **Optimized Algorithms**: Efficient ray-geometry intersection calculations
- **Configurable Quality**: Adjustable resolution and anti-aliasing settings

## Testing

The project includes extensive unit tests covering:
- Geometric intersection algorithms
- Camera ray construction
- Lighting calculations
- Material property handling
- Scene composition
- Image generation

Run the test suite to see the engine in action and generate sample images.

## Documentation

Full API documentation is available in the `doc/` directory, generated using JavaDoc.

## License

This project is developed as part of coursework for ISE5785_8831_5135.

## Contributing

This is an academic project. For educational purposes, refer to the comprehensive test suite and documentation for understanding the implementation details.
