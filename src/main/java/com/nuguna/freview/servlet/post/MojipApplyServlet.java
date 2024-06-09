package com.nuguna.freview.servlet.post;

import com.nuguna.freview.dao.request.RequestDAO;
import com.nuguna.freview.entity.request.Request;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mojipBoard/detail/apply")
public class MojipApplyServlet extends HttpServlet {

  RequestDAO requestDAO = new RequestDAO();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    Request request = new Request();

    int fromMemberSeq = Integer.parseInt(req.getParameter("applicantSeq"));
    int toMemberSeq = Integer.parseInt(req.getParameter("writerSeq"));
    String gubun = "AP";
    int fromPostSeq = Integer.parseInt(req.getParameter("postSeq"));
    String status = "pending";
    LocalDateTime now = LocalDateTime.now();

    request.setFromMemberSeq(fromMemberSeq);
    request.setToMemberSeq(toMemberSeq);
    request.setGubun(gubun);
    request.setFromPost(fromPostSeq);
    request.setStatus(status);
    request.setCreatedAt(Timestamp.valueOf(now));
    request.setUpdatedAt(Timestamp.valueOf(now));

    boolean isInserted = requestDAO.insertApply(request);

    if (isInserted) {
      resp.setStatus(HttpServletResponse.SC_OK);
    } else {
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
