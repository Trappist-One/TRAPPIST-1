<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="消息标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入消息标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="消息类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择消息类型：1短信 2邮件 3微信" clearable size="small">
          <el-option label="请选择字典生成" value=""/>
        </el-select>
      </el-form-item>
      <el-form-item label="接收人" prop="receiver">
        <el-input
          v-model="queryParams.receiver"
          placeholder="请输入接收人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="推送时间" prop="sendTime">
        <el-date-picker clearable size="small"
                        v-model="queryParams.sendTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择推送时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="推送状态" prop="sendStatus">
        <el-select v-model="queryParams.sendStatus" placeholder="请选择推送状态" clearable size="small">
          <el-option label="请选择字典生成" value=""/>
        </el-select>
      </el-form-item>
      <el-form-item label="发送次数" prop="sendNum">
        <el-input
          v-model="queryParams.sendNum"
          placeholder="请输入发送次数"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPerm="['sms_del']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPerm="['sms_export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sysSmsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="ID" align="center" prop="id" width="50"/>
      <el-table-column label="消息标题" align="center" prop="title" width="200"/>
      <el-table-column label="消息类型" align="center" prop="type" :formatter="smsTypeFormat"/>
      <el-table-column label="接收人" align="center" prop="receiver" width="200"/>
      <el-table-column label="推送内容" align="center" :show-overflow-tooltip='true' prop="content" width="400"/>
      <el-table-column label="推送时间" align="center" prop="sendTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.sendTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="推送状态" align="center" prop="sendStatus" :formatter="sendStatusFormat"/>
      <el-table-column label="发送次数" align="center" prop="sendNum"/>
      <el-table-column label="创建日期" align="center" prop="createTime" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remarks" :show-overflow-tooltip='true' />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row,scope.index)"
            v-hasPerm="['sms_view']"
          >详细
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPerm="['sms_del']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.current"
      :limit.sync="queryParams.size"
      @pagination="getList"
    />

    <!-- 详细 -->
    <el-dialog :title="title" :visible.sync="open" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="消息标题" prop="title">
              <el-input v-model="form.title" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="消息类型" prop="type">
              <el-select v-model="form.type" :formatter="smsTypeFormat" disabled>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="接收人" prop="receiver">
              <el-input v-model="form.receiver" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="发送参数" prop="esParam">
              <el-input v-model="form.esParam" type="textarea" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="推送内容" prop="content">
              <editor v-model="form.content" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="推送时间" prop="sendTime">
              <el-date-picker clearable size="small"
                              v-model="form.sendTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              disabled>
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="推送状态">
              <el-input v-model="form.sendStatus" :formatter="sendStatusFormat" disabled>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="发送次数" prop="sendNum">
              <el-input v-model="form.sendNum" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="失败原因" prop="esResult">
              <el-input v-model="form.esResult" type="textarea" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="创建日期" prop="sendTime">
              <el-date-picker clearable size="small"
                              v-model="form.createTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              disabled>
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remarks">
              <el-input v-model="form.remarks" type="textarea" disabled/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="open = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listSms, getSms, delSms, exportSms} from "@/api/system/sms";
import editor from "@/components/Editor/index";

export default {
  name: "sms",
  components: {
    'editor': editor
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 消息中心表格数据
      sysSmsList: [],
      smsTypes: [],
      sendStatus: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        title: null,
        type: null,
        receiver: null,
        param: null,
        content: null,
        sendTime: null,
        sendStatus: null,
        sendNum: null,
        result: null,
        remark: null,
        createTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      options: {
        theme: "snow",
        bounds: document.body,
        debug: "warn",
        modules: {
          // 工具栏配置
          toolbar: []
        },
        readOnly: true,
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sms_type").then(response => {
      this.smsTypes = response.data;
    });
    this.getDicts("send_type").then(response => {
      this.sendStatus = response.data;
    });
  },
  methods: {
    /** 查询消息中心列表 */
    getList() {
      this.loading = true;
      listSms(this.queryParams).then(response => {
        this.sysSmsList = response.data;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 系统内置字典翻译
    smsTypeFormat(row, column) {
      return this.selectDictLabel(this.smsTypes, row.type);
    },
    // 字典状态字典翻译
    sendStatusFormat(row, column) {
      return this.selectDictLabel(this.sendStatus, row.sendStatus);
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        title: null,
        type: null,
        receiver: null,
        param: null,
        content: null,
        sendTime: null,
        sendStatus: "0",
        sendNum: null,
        result: null,
        remarks: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.current = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 详细按钮操作 */
    handleView(row) {
      this.open = true;
      this.form = row;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除消息中心编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delSms(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有消息中心数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportSms(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
