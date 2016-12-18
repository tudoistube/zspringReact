<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--...과제24_2.B.
		/z09_sts372/src/main/webapp/WEB-INF/views/zboard/success.jsp 복사한 것.
		"https://almsaeedstudio.com/AdminLTE"에서 Latest Orders 부분 가져옴.
		"http://office.jangone.co.kr/Close/main.jsp" 페이지 역할을 함.
 --%>
  <script type="text/javascript">
	function excelDown(obj, url)
	{
		var caption = "clicked...";
		
		if("jstl_valid_dt" == "")
		{
			alert("학습실적일자가 존재 하지 않습니다.");
			return;
		}
		
		var subPram = "";
		if(obj.name == "s01")		subPram = "&study_tp=01";
		else if (obj.name == "s02") subPram = "&study_tp=02";

		//if(document.all.sql_flg.checked) subPram = "&sql_flg=true";

		//ifmExcel.location.href = url+"?vaild_dt=jstl_valid_dt"+subPram;
		self.location = "/web/zclose/listAll_close009";
	}
  </script> 
<%@include file="../zinclude/header.jsp"%>

<!-- Main content -->
<section class="content">
	<div class="row">
<!-- left column -->
<div class="col-md-12">
	<!-- general form elements -->

	<div class="box">
<div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">Section Title 1</h3>

              <div class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                </button>
                <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
              </div>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <div class="table-responsive">
                <table class="table no-margin">
                  <thead>
                  <tr>
                    <th>Item</th>
                    <th>Jsp Page Name</th>
                    <th>Popularity</th>
                  </tr>
                  </thead>
                  <tbody>
<%--   ...과제25_1.C.엑셀파일 다운로드 호출경로   --%>                  
                  <tr>
                    <td>1-1. 9번째 엑셀 자료(s)</td>
                    <td>009</td>
                    <td>
                      <a href="javascript:void(0)" class="btn btn-sm btn-info btn-flat pull-left" onclick="excelDown(this, 'listAll_close009.jsp')">다운로드</a>
                    </td>
                  </tr>                  
                  </tbody>
                </table>
              </div>
              <!-- /.table-responsive -->
            </div>
            <!-- /.box-body -->
            <div class="box-footer clearfix">
              <a href="javascript:void(0)" class="btn btn-sm btn-info btn-flat pull-left">Place New Order</a>
              <a href="javascript:void(0)" class="btn btn-sm btn-default btn-flat pull-right">View All Orders</a>
            </div>
            <!-- /.box-footer -->
          </div>		
	</div>
</div>
<!--/.col (left) -->

	</div>
	<!-- /.row -->
</section>
<!-- /.content -->
</div>
<!-- /.content-wrapper -->

<%@include file="../zinclude/footer.jsp"%>