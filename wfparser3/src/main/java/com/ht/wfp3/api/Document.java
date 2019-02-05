package com.ht.wfp3.api;

import com.ht.wfp3.api.acceptance.Unknown;

/**
 * The Document View of the OBJ file.
 * 
 * This view of the file is line oriented with the first line starting at line number "1". The
 * objective of this view is to provide a convenient view appropriate for parsers. All operations
 * are based on line number and aside from checking for nulls and valid line numbers, no validation
 * of the OBJ data is performed. Thus, if invalid data is inserted within a multi-line element, no
 * error will be generated until the logical view of this data is retrieved since, at that point,
 * validation is required.
 * 
 * @author nickl
 *
 */
public interface Document {

  Node peekAtNodeAtLine(int lineNumber);

  void insertNodeAtLine(GeoVertex geoVertex, int lineNumber);

  void insertNodeAtLine(TexVertex texVertex, int lineNumber);

  void insertNodeAtLine(NormalVertex normalVertex, int lineNumber);

  void insertNodeAtLine(ParamVertex paramVertex, int lineNumber);

  void insertNodeAtLine(Point point, int lineNumber);

  void insertNodeAtLine(Line line, int lineNumber);

  void insertNodeAtLine(Face face, int lineNumber);

  void insertNodeAtLine(CurveOrSurface cstype, int lineNumber);

  void insertNodeAtLine(Degree deg, int lineNumber);

  void insertNodeAtLine(BasisMatrix bmat, int lineNumber);

  void insertNodeAtLine(StepSize step, int lineNumber);

  void insertNodeAtLine(Curve curv, int lineNumber);

  void insertNodeAtLine(Curve2D curv2, int lineNumber);

  void insertNodeAtLine(Surface surf, int lineNumber);

  void insertNodeAtLine(Call call, int lineNumber);

  void insertNodeAtLine(Csh csh, int lineNumber);

  void insertNodeAtLine(Parm parm, int lineNumber);

  void insertNodeAtLine(Trim trim, int lineNumber);

  void insertNodeAtLine(Hole hole, int lineNumber);

  void insertNodeAtLine(SpecialCurve scrv, int lineNumber);

  void insertNodeAtLine(SpecialPoint sp, int lineNumber);

  void insertNodeAtLine(End end, int lineNumber);

  void insertNodeAtLine(Connect con, int lineNumber);

  void insertNodeAtLine(GroupNameList g, int lineNumber);

  void insertNodeAtLine(SmoothingGroup s, int lineNumber);

  void insertNodeAtLine(MergingGroup mg, int lineNumber);

  void insertNodeAtLine(ObjectName o, int lineNumber);

  void insertNodeAtLine(Bevel bevel, int lineNumber);

  void insertNodeAtLine(ColorInterpolation c_interp, int lineNumber);

  void insertNodeAtLine(DissolveInterpolation d_interp, int lineNumber);

  void insertNodeAtLine(LevelOfDetail lod, int lineNumber);

  void insertNodeAtLine(MapLib maplib, int lineNumber);

  void insertNodeAtLine(UseMaterial usemtl, int lineNumber);

  void insertNodeAtLine(UseMap usemap, int lineNumber);

  void insertNodeAtLine(MaterialLib mtllib, int lineNumber);

  void insertNodeAtLine(ShadowObject shadow_obj, int lineNumber);

  void insertNodeAtLine(RayTracingObject trace_obj, int lineNumber);

  void insertNodeAtLine(CparmCurveApprox ctech, int lineNumber);

  void insertNodeAtLine(CspaceCurveApprox ctech, int lineNumber);

  void insertNodeAtLine(CurvCurveApprox ctech, int lineNumber);

  void insertNodeAtLine(CparmaSurfaceApprox stech, int lineNumber);

  void insertNodeAtLine(CparmbSurfaceApprox stech, int lineNumber);

  void insertNodeAtLine(CspaceSurfaceApprox stech, int lineNumber);

  void insertNodeAtLine(CurvSurfaceApprox stech, int lineNumber);

  void insertNodeAtLine(Blank blank, int lineNumber);

  void insertNodeAtLine(Unknown unknown, int lineNumber);
}
