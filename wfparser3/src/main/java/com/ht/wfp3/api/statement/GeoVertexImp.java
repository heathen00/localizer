package com.ht.wfp3.api.statement;

import java.math.BigDecimal;

class GeoVertexImp extends StatementImp implements GeoVertex {
  private static final String KEYWORD = "v";
  private static final boolean CAN_COMMENT = true;

  private BigDecimal xCoord;
  private BigDecimal yCoord;
  private BigDecimal zCoord;
  private BigDecimal wCoord;

  GeoVertexImp(BigDecimal xCoord, BigDecimal yCoord, BigDecimal zCoord, BigDecimal wCoord) {
    super(KEYWORD, CAN_COMMENT);
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.zCoord = zCoord;
    this.wCoord = wCoord;
  }

  GeoVertexImp(GeoVertex geoVertex) {
    this(geoVertex.getXCoord(), geoVertex.getYCoord(), geoVertex.getZCoord(),
        geoVertex.getWCoord());
  }

  @Override
  public BigDecimal getXCoord() {
    return xCoord;
  }

  @Override
  public BigDecimal getYCoord() {
    return yCoord;
  }

  @Override
  public BigDecimal getZCoord() {
    return zCoord;
  }

  @Override
  public BigDecimal getWCoord() {
    return wCoord;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((wCoord == null) ? 0 : wCoord.hashCode());
    result = prime * result + ((xCoord == null) ? 0 : xCoord.hashCode());
    result = prime * result + ((yCoord == null) ? 0 : yCoord.hashCode());
    result = prime * result + ((zCoord == null) ? 0 : zCoord.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    GeoVertexImp other = (GeoVertexImp) obj;
    if (wCoord == null) {
      if (other.wCoord != null) {
        return false;
      }
    } else if (!wCoord.equals(other.wCoord)) {
      return false;
    }
    if (xCoord == null) {
      if (other.xCoord != null) {
        return false;
      }
    } else if (!xCoord.equals(other.xCoord)) {
      return false;
    }
    if (yCoord == null) {
      if (other.yCoord != null) {
        return false;
      }
    } else if (!yCoord.equals(other.yCoord)) {
      return false;
    }
    if (zCoord == null) {
      if (other.zCoord != null) {
        return false;
      }
    } else if (!zCoord.equals(other.zCoord)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "GeoVertexImp [xCoord=" + xCoord + ", yCoord=" + yCoord + ", zCoord=" + zCoord
        + ", wCoord=" + wCoord + ", super.toString()=" + super.toString() + "]";
  }
}
