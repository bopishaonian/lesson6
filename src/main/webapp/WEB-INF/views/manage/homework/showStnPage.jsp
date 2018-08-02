<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="lesson" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="lessonTag" uri="http://com.biz.lesson/tag/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<lesson:page title="系统管理-学生管理">
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
							<li class="active">学生管理</li>
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
									学生管理
									<span class=" btn-group pull-right">
                                    <a href="manage/student/showAddStn.do">
                                        <button>添加</button>
                                    </a>

                            	</span>
								</h3>
								<table id="simple-table" class="table  table-bordered table-hover">
									<!--标题-->
									<thead>
										<tr>
											<th class="detail-col">详细</th>
											<th>学号</th>
											<th>姓名</th>
											<th class="hidden-480">班级</th>
											<th class="hidden-480">平均分</th>
											<th width="70px">分数录入</th>
											<th width="50px">选课</th>
											<th>操作</th>
										</tr>
									</thead>
									<!--循环体-->
									<tbody>
										<c:forEach items="${stns}" var="stn">
											<tr>
												<td class="center">
													<div class="action-buttons">
														<a href="#" class="green bigger-140 show-details-btn" title="Show Details">
															<i class="ace-icon fa fa-angle-double-down"></i>
															<span class="sr-only">Details</span>
														</a>
													</div>
												</td>

												<td>${stn.student.num}</td>
												<td>${stn.student.name}</td>
												<td>${stn.name}</td>
												<td>${stn.student.avgNum}</td>
												<td><a href="/manage/student/writeScorePage.do?id=${stn.student.studentId}">分数录入</a></td>
												<td><a href="/manage/student/chooseGradePage.do?id=${stn.student.studentId}">选课</a></td>
												<td>
													<div class="hidden-sm hidden-xs btn-group">
														<!--修改-->
														<a href="/manage/student/showEdit.do?id=${stn.student.studentId}">
															<button class="btn btn-xs btn-info">
																<i class="ace-icon fa fa-pencil bigger-120"></i>
															</button>
														</a >
														<!--删除-->
														<a href="/manage/student/del.do?id=${stn.student.studentId}">
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

											<tr class="detail-row">
												<td colspan="8">
													<div class="table-detail">
														<div class="row">
															<div class="col-xs-12 col-sm-2">
																<div class="text-center">
																	<img height="150" width="110" class="thumbnail inline no-margin-bottom" alt="Domain Owner's Avatar"
																		 src="https://huangbo-oss.oss-cn-shenzhen.aliyuncs.com/${stn.student.img}" />
																	<br />
																	<div class="width-80 label label-info label-xlg arrowed-in arrowed-in-right">
																		<div class="inline position-relative">
																			<a class="user-title-label" href="#">
																				<i class="ace-icon fa fa-circle light-green"></i>
																				&nbsp;
																				<span class="white">${stn.student.name}</span>
																			</a>
																		</div>
																	</div>
																</div>
															</div>

															<div class="col-xs-12 col-sm-7">
																<div class="space visible-xs"></div>

																<div class="profile-user-info profile-user-info-striped">
																	<div class="profile-info-row">
																		<div class="profile-info-name"> 姓名 </div>

																		<div class="profile-info-value">
																			<span>${stn.student.name}</span>
																		</div>
																	</div>

																	<div class="profile-info-row">
																		<div class="profile-info-name"> 学号 </div>

																		<div class="profile-info-value">
																			<i class="fa fa-map-marker light-orange bigger-110"></i>
																			<span>${stn.student.num}</span>
																		</div>
																	</div>

																	<div class="profile-info-row">
																		<div class="profile-info-name"> 性别 </div>

																		<div class="profile-info-value">
																			<span>${stn.student.sex}</span>
																		</div>
																	</div>

																	<div class="profile-info-row">
																		<div class="profile-info-name"> 出生日期 </div>

																		<div class="profile-info-value">
																			<span><fmt:formatDate value="${stn.student.date}" pattern="yyyy-MM-dd"/></span>
																		</div>
																	</div>

																	<div class="profile-info-row">
																		<div class="profile-info-name"> 选课门数</div>

																		<div class="profile-info-value">
																			<span>${stn.student.subjectNum}</span>
																		</div>
																	</div>

																	<div class="profile-info-row">
																		<div class="profile-info-name">平均分</div>

																		<div class="profile-info-value">
																			<span>${stn.student.avgNum}</span>
																		</div>
																	</div>
																</div>
															</div>

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