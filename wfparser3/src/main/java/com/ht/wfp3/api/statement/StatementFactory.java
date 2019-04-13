package com.ht.wfp3.api.statement;

import java.util.List;
import com.ht.wfp3.api.statement.BasisMatrix.Axis;

/**
 * The OBJ element factory.
 * 
 * The purpose of this factory is to hide the details of how the elements are created.
 * 
 * @author nickl
 *
 */
public final class StatementFactory {
  private static final StatementFactory STATEMENT_FACTORY_SINGLETON = new StatementFactory();

  public static StatementFactory createStatementFactory() {
    return STATEMENT_FACTORY_SINGLETON;
  }

  public GeoVertex createGeoVertex(String xCoord, String yCoord, String zCoord, String wCoord) {
    return new GeoVertexImp(xCoord, yCoord, zCoord, wCoord);
  }

  public GeoVertex copyGeoVertex(GeoVertex geoVertex) {
    return new GeoVertexImp(geoVertex);
  }

  public TexVertex createTexVertex(String uCoord, String vCoord, String wCoord) {
    return new TexVertexImp(uCoord, vCoord, wCoord);
  }

  public TexVertex copyTexVertex(TexVertex texVertex) {
    return new TexVertexImp(texVertex);
  }

  public NormalVertex createNormalVertex(String iCoord, String jCoord, String kCoord) {
    return new NormalVertexImp(iCoord, jCoord, kCoord);
  }

  public NormalVertex copyNormalVertex(NormalVertex normalVertex) {
    return new NormalVertexImp(normalVertex);
  }

  public ParamVertex createParamVertex(String uCoord, String vCoord, String wCoord) {
    return new ParamVertexImp(uCoord, vCoord, wCoord);
  }

  public ParamVertex copyParamVertex(ParamVertex paramVertex) {
    return new ParamVertexImp(paramVertex);
  }

  public VertexReferenceGroupBuilder createVertexReferenceGroupBuilder() {
    return new VertexReferenceGroupBuilderImp();
  }

  public VertexReferenceGroup copyVertexReferenceGroup(VertexReferenceGroup vertexReferenceGroup) {
    return new VertexReferenceGroupImp((VertexReferenceGroupImp) vertexReferenceGroup);
  }

  // Not published.
  VertexReferenceImp createVertexReference(VertexReferenceImp.Type type, Integer vertexIndex,
      boolean isSet) {
    return new VertexReferenceImp(type, vertexIndex, isSet);
  }

  // Not published.
  VertexReferenceImp copyVertexReference(VertexReferenceImp vertexReference) {
    return new VertexReferenceImp(vertexReference);
  }

  public Point createPoint() {
    return new PointImp();
  }

  public Point copyPoint(Point point) {
    return new PointImp(point);
  }

  public Line createLine() {
    return new LineImp();
  }

  public Line copyLine(Line line) {
    return new LineImp(line);
  }

  public Face createFace() {
    return new FaceImp();
  }

  public Face copyFace(Face face) {
    return new FaceImp(face);
  }

  public CurveOrSurfaceType createCurveOrSurface(String rational, CurveOrSurfaceType.Key typeKey) {
    return new CurveOrSurfaceTypeImp(rational, typeKey);
  }

  public Statement copyCurveOrSurfaceType(CurveOrSurfaceType cstype) {
    return new CurveOrSurfaceTypeImp(cstype);
  }

  public Degree createDegree(Integer uAxisDegree, Integer vAxisDegree) {
    // TODO Auto-generated method stub

    return null;
  }

  public MatrixBuilder createMatrixBuilder() {
    // TODO Auto-generated method stub
    return null;
  }

  public BasisMatrix createBasisMatrix(Axis axis, Matrix matrix) {
    // TODO Auto-generated method stub

    return null;
  }

  public StepSize createStepSize(String stepSizeInUAxis, String stepSizeInVAxis) {
    // TODO Auto-generated method stub

    return null;
  }

  public Curve createCurve(String startingParameterValue, String endingParameterValue) {
    // TODO Auto-generated method stub

    return null;
  }

  public Curve2D createCurve2D() {
    // TODO Auto-generated method stub

    return null;
  }

  public Surface createSurface(String startingParameterValueUAxis, String endingParameterValueUAxis,
      String startingParameterValueVAxis, String endingParameterValueVAxis) {
    // TODO Auto-generated method stub

    return null;
  }

  public Call createCall(String fileName) {
    // TODO Auto-generated method stub

    return null;
  }

  public Csh createCsh(String ignoreError, String command) {
    // TODO Auto-generated method stub

    return null;
  }

  public Parm createParm(String axis) {
    // TODO Auto-generated method stub

    return null;
  }

  public Trim createTrim() {
    // TODO Auto-generated method stub

    return null;
  }

  public Curve2DReference createCurve2DReference(String startingParameterValue,
      String endingParameterValue, String curve2DIndex) {
    // TODO Auto-generated method stub

    return null;
  }

  public Hole createHole() {
    // TODO Auto-generated method stub

    return null;
  }

  public SpecialCurve createSpecialCurve() {
    // TODO Auto-generated method stub

    return null;
  }

  public SpecialPoint createSpecialPoint() {
    // TODO Auto-generated method stub
    return null;
  }

  public End createEnd() {
    // TODO Auto-generated method stub

    return null;
  }

  public Connect createConnect(String firstSurfaceIndex,
      Curve2DReference curve2dReferenceForFirstSurface, String secondSurfaceIndex,
      Curve2DReference curve2dReferenceForSecondSurface) {
    // TODO Auto-generated method stub

    return null;
  }

  public GroupNameList createGroupName() {
    // TODO Auto-generated method stub

    return null;
  }

  public SmoothingGroup createSmoothingGroup(String groupNumberOrOff) {
    // TODO Auto-generated method stub

    return null;
  }

  public MergingGroup createMergingGroup(String groupNumberOrOff, String resolution) {
    // TODO Auto-generated method stub

    return null;
  }

  public ObjectName createObjectName(String objectName) {
    // TODO Auto-generated method stub

    return null;
  }

  public Bevel createBevel(String onOrOff) {
    // TODO Auto-generated method stub

    return null;
  }

  public ColorInterpolation createColorInterpolation(String onOrOff) {
    // TODO Auto-generated method stub

    return null;
  }

  public DissolveInterpolation createDissolveInterpolation(String onOrOff) {
    // TODO Auto-generated method stub

    return null;
  }

  public LevelOfDetail createLevelOfDetail(String levelOfDetail) {
    // TODO Auto-generated method stub

    return null;
  }

  public MapLib createMapLib() {
    // TODO Auto-generated method stub

    return null;
  }

  public UseMaterial createUseMaterial(String materialName) {
    // TODO Auto-generated method stub

    return null;
  }

  public UseMap createUseMap(String mapNameOrOff) {
    // TODO Auto-generated method stub

    return null;
  }

  public MaterialLib createMaterialLib() {
    // TODO Auto-generated method stub

    return null;
  }

  public ShadowObject createShadowObject(String shadowObjectFileName) {
    // TODO Auto-generated method stub

    return null;
  }

  public RayTracingObject createRayTracingObject(String rayTracingObjectFileName) {
    // TODO Auto-generated method stub

    return null;
  }

  public CparmCurveApprox createCparmCurveApprox(String resolution) {
    // TODO Auto-generated method stub

    return null;
  }

  public CspaceCurveApprox createCspaceCurveApprox(String maxLength) {
    // TODO Auto-generated method stub

    return null;
  }

  public CurvCurveApprox createCurvCurveAprox(String maxDist, String maxAngleInDegrees) {
    // TODO Auto-generated method stub

    return null;
  }

  public CparmaSurfaceApprox createCparmaSurfaceApprox(String resolutionForUAxis,
      String resolutionForVAxis) {
    // TODO Auto-generated method stub

    return null;
  }

  public CparmbSurfaceApprox createCparmbSurfaceApprox(String resolutionForUAndVAxes) {
    // TODO Auto-generated method stub

    return null;
  }

  public CspaceSurfaceApprox createCspaceSurfaceApprox(String maxLength) {
    // TODO Auto-generated method stub

    return null;
  }

  public CurvSurfaceApprox createCurvSurfaceApprox(String maxDist, String maxAngleInDegrees) {
    // TODO Auto-generated method stub

    return null;
  }

  public Blank createBlank() {
    // TODO Auto-generated method stub

    return null;
  }

  public Unknown createUnknown(List<String> tokens) {
    // TODO Auto-generated method stub

    return null;
  }

  public Comment createComment(String commentString) {
    // TODO Auto-generated method stub

    return null;
  }

  public boolean isSupportedStatement(Statement statement) {
    return statement instanceof StatementImp;
  }
}
