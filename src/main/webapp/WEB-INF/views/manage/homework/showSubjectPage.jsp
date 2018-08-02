<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="lesson" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="lessonTag" uri="http://com.biz.lesson/tag/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<lesson:page title="系统管理-学科管理">
	<jsp:body>
	<div class="no-skin">
		<div class="main-container ace-save-state" id="main-container">
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								<a href="#">Home</a>
							</li>

							<li>
								<a href="#">系统信息</a>
							</li>
							<li class="active">学科管理</li>
						</ul>
						<div class="nav-search" id="nav-search">
							<form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
							</form>
						</div><!-- /.nav-search -->
					</div>
				</div><!-- /.ace-settings-container -->
				<div class="row">
					<div class="col-xs-12">
						<div class="row">
							<div class="col-xs-12">
								<!--添加按钮-->
								<h3 class="header smaller lighter blue">
									学科管理
									<span class=" btn-group pull-right">
                                    <a href="/manage/subject/ShowAdd.do">
                                        <button>添加</button>
                                    </a>

                            	</span>
								</h3>
								<table id="simple-table" class="table  table-bordered table-hover">
									<!--标题-->
									<thead>
										<tr>
											<th>学科名</th>
											<th>选修人数</th>
											<th class="hidden-480">平均分</th>
											<th class="hidden-480">操作</th>
										</tr>
									</thead>
									<!--循环体-->
									<tbody>
										<c:forEach items="${subjects}" var="subject">
											<tr>
												<td>${subject.name}</td>
												<td>${subject.count}</td>
												<td>${subject.avg}</td>
												<td>
													<div class="hidden-sm hidden-xs btn-group">
														<!--修改-->
														<a href="/manage/subject/showEdit.do?subjectId=${subject.subjectId}&count=${subject.count}&avg=${subject.avg}">
															<button class="btn btn-xs btn-info">
																<i class="ace-icon fa fa-pencil bigger-120"></i>
															</button>
														</a >
														<!--删除-->
														<a href="/manage/subject/del.do?name=${subject.name}">
															<button class="btn btn-xs btn-danger" >
																<i class="ace-icon fa fa-trash-o bigger-120"></i>
															</button>
														</a>
													</div>

													<div class="hidden-md hidden-lg">
														<div class="inline pos-rel">
															<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
																<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
															</button>

															<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
																<li>
																	<a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																				<span class="blue">
																					<i class="ace-icon fa fa-search-plus bigger-120"></i>
																				</span>
																	</a>
																</li>

																<li>
																	<a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																				<span class="green">
																					<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																				</span>
																	</a>
																</li>

																<li>
																	<a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																				<span class="red">
																					<i class="ace-icon fa fa-trash-o bigger-120"></i>
																				</span>
																	</a>
																</li>
															</ul>
														</div>
													</div>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div><!-- /.span -->
						</div><!-- /.row -->
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.page-content -->
		</div>
	</div>
</jsp:body>
</lesson:page>