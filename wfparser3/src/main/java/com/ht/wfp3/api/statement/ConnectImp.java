package com.ht.wfp3.api.statement;

class ConnectImp extends StatementImp implements Connect {
  private static final String KEYWORD = "con";

  private final Integer firstSurfaceIndex;
  private final Curve2DReference firstSurfaceCurve2DReference;
  private final Integer secondSurfaceIndex;
  private final Curve2DReference secondSurfaceCurve2DReference;

  ConnectImp(Integer firstSurfaceIndex, Curve2DReference firstSurfaceCurve2DReference,
      Integer secondSurfaceIndex, Curve2DReference secondSurfaceCurve2DReference) {
    super(KEYWORD);
    this.firstSurfaceIndex = firstSurfaceIndex;
    this.firstSurfaceCurve2DReference = firstSurfaceCurve2DReference;
    this.secondSurfaceIndex = secondSurfaceIndex;
    this.secondSurfaceCurve2DReference = secondSurfaceCurve2DReference;
  }

  ConnectImp(Connect con) {
    this(con.getFirstSurfaceIndex(), con.getFirstSurfaceCurve2DReference(),
        con.getSecondSurfaceIndex(), con.getSecondSurfaceCurve2DReference());
  }

  @Override
  public Integer getFirstSurfaceIndex() {
    return firstSurfaceIndex;
  }

  @Override
  public Curve2DReference getFirstSurfaceCurve2DReference() {
    return firstSurfaceCurve2DReference;
  }

  @Override
  public Integer getSecondSurfaceIndex() {
    return secondSurfaceIndex;
  }

  @Override
  public Curve2DReference getSecondSurfaceCurve2DReference() {
    return secondSurfaceCurve2DReference;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result
        + ((firstSurfaceCurve2DReference == null) ? 0 : firstSurfaceCurve2DReference.hashCode());
    result = prime * result + ((firstSurfaceIndex == null) ? 0 : firstSurfaceIndex.hashCode());
    result = prime * result
        + ((secondSurfaceCurve2DReference == null) ? 0 : secondSurfaceCurve2DReference.hashCode());
    result = prime * result + ((secondSurfaceIndex == null) ? 0 : secondSurfaceIndex.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    ConnectImp other = (ConnectImp) obj;
    if (firstSurfaceCurve2DReference == null) {
      if (other.firstSurfaceCurve2DReference != null)
        return false;
    } else if (!firstSurfaceCurve2DReference.equals(other.firstSurfaceCurve2DReference))
      return false;
    if (firstSurfaceIndex == null) {
      if (other.firstSurfaceIndex != null)
        return false;
    } else if (!firstSurfaceIndex.equals(other.firstSurfaceIndex))
      return false;
    if (secondSurfaceCurve2DReference == null) {
      if (other.secondSurfaceCurve2DReference != null)
        return false;
    } else if (!secondSurfaceCurve2DReference.equals(other.secondSurfaceCurve2DReference))
      return false;
    if (secondSurfaceIndex == null) {
      if (other.secondSurfaceIndex != null)
        return false;
    } else if (!secondSurfaceIndex.equals(other.secondSurfaceIndex))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "ConnectImp [firstSurfaceIndex=" + firstSurfaceIndex + ", firstSurfaceCurve2DReference="
        + firstSurfaceCurve2DReference + ", secondSurfaceIndex=" + secondSurfaceIndex
        + ", secondSurfaceCurve2DReference=" + secondSurfaceCurve2DReference + ", super.toString()="
        + super.toString() + "]";
  }
}