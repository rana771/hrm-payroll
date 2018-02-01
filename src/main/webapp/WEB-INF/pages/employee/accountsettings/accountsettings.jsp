<div class="row">
    <div class="col-md-12">
            <div class="content-box mrg15B">
                <h3 class="content-box-header clearfix">
                    Change Password
                    <div class="font-size-11 float-right">
                        <a href="#" title="">
                            <i class="glyph-icon mrg5R opacity-hover icon-plus"></i>
                        </a>
                        <a href="#" title="">
                            <i class="glyph-icon opacity-hover icon-cog"></i>
                        </a>
                    </div>
                </h3>
                <div class="content-box-wrapper pad0T clearfix">
                    <form method="POST" modelAttribute="employee" id="accountSettingformId"
                               class="form-horizontal bordered-row" data-parsley-validate="">
                        <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label" style="text-align: left">PIN:</label>
                            <div class="col-sm-6">
                                <input type="text" id="accpin" name="pin" required class="form-control" value="${employee.pin}" placeholder="pin">
                                <input type="hidden" id="userid" name="id" required class="form-control" value="${employee.user.id}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label" style="text-align: left">Full Name:</label>
                            <div class="col-sm-6">
                                <input type="text" id="accFullName" name="fullName" required class="form-control" value="${employee.fullName}" placeholder="pin">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label" style="text-align: left">Email:</label>
                            <div class="col-sm-6">
                                <input type="text" id="accemail" name="email" required class="form-control" value="${employee.email}" placeholder="email">
                            </div>
                        </div>
                        </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label" style="text-align: left">Username:</label>
                            <div class="col-sm-6">
                            <input type="text" id="accusername" name="userName" required class="form-control" value="" placeholder="username">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label" style="text-align: left">New password:</label>
                            <div class="col-sm-6">
                                <input type="password" required class="form-control" id="newPassword" name="password" value="" placeholder="New Password">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label" style="text-align: left">Repeat password:</label>
                            <div class="col-sm-6 passmatchcls">
                                <input type="password"  required class="form-control " id="repeatPassword" value="" placeholder="Repeat Password">
                            </div>
                        </div>
                      </div>
                        <div class="button-pane mrg20T">
                        <button id="saveAccounts" class="btn btn-success" onclick="saveAccountSettings()">Save Password</button>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>

