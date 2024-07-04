package com.nuguna.freview.dao.member.cust.page;

import com.nuguna.freview.dto.cust.brand.CustMyLikePostDto;
import com.nuguna.freview.dto.cust.brand.CustMyZzimStoreDto;
import com.nuguna.freview.dto.cust.brand.CustZzimedMeStoreDto;
import java.util.List;

// REST API 방식으로 구현해야할듯 ( URL 이동 X, 뷰만 바뀌게 )
// 1. 내가 좋아요한 글 가져오기 ( 기본값 )
// 2. 내가 찜한 스토어 가져오기
// 3. 나를 찜한 스토어 가져오기
public class CustMyActivityDAO {

  /*
  좋아요한 글
   */
  public List<CustMyLikePostDto> getCustActivityInfo(int memberSeq) {
    return null;
  }

  public List<CustMyZzimStoreDto> getCustZzimStores(int membeSeq) {
    return null;
  }

  public List<CustZzimedMeStoreDto> getZzimedMeStores(int membeSeq) {
    return null;
  }

}
