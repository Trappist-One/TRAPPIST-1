INSERT INTO `sys_menu` VALUES (58, '工作流', 3, NULL, 1, 'M', 'activiti', '#', NULL, 'skill', '1', 4, '0', 'admin', '2020-03-17 18:22:36', 'admin', '2020-03-20 23:09:48', NULL, '0');

INSERT INTO `sys_menu` VALUES (85, '模型管理', 58, NULL, 1, 'C', 'model', 'activiti/model/index', NULL, 'edit', '1', 1, '0', 'admin', '2020-03-24 12:48:54', NULL, NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (86, '模型新增', 85, NULL, 1, 'F', NULL, '#', 'model_add', NULL, '1', 1, '0', 'admin', '2020-03-24 12:52:47', NULL, NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (87, '模型删除', 85, NULL, 1, 'F', NULL, '#', 'model_del', NULL, '1', 2, '0', 'admin', '2020-03-24 12:53:43', NULL, NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (88, '模型查看', 85, NULL, 1, 'F', NULL, '#', 'model_view', NULL, '1', 3, '0', 'admin', '2020-03-24 12:53:56', NULL, NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (89, '模型部署', 85, NULL, 1, 'F', NULL, '#', 'model_deploy', NULL, '1', 4, '0', 'admin', '2020-03-24 12:54:19', NULL, NULL, NULL, '0');

INSERT INTO `sys_menu` VALUES (121, '流程定义', 58, NULL, 1, 'C', 'process', 'activiti/process/index', NULL, 'list', '0', 2, '0', 'admin', '2020-04-04 22:33:12', NULL, NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (122, '模型设计', 85, NULL, 1, 'F', NULL, '#', 'model_design', NULL, '0', 4, '0', 'admin', '2020-04-04 22:36:13', NULL, NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (123, '流程图', 121, NULL, 1, 'F', NULL, '#', 'process_img', NULL, '0', 1, '0', 'admin', '2020-04-04 22:49:07', NULL, NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (124, '流程状态改变', 121, NULL, 1, 'F', NULL, '#', 'process_change', NULL, '0', 2, '0', 'admin', '2020-04-04 22:49:46', NULL, '2020-04-05 00:40:17', NULL, '0');
INSERT INTO `sys_menu` VALUES (125, '流程删除', 121, NULL, 1, 'F', NULL, '#', 'process_del', NULL, '0', 3, '0', 'admin', '2020-04-04 22:50:05', NULL, NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (126, '流程查看', 121, NULL, 1, 'F', NULL, '#', 'process_view', NULL, '0', 4, '0', 'admin', '2020-04-04 22:50:30', 'admin', '2020-04-04 22:50:35', NULL, '0');
INSERT INTO `sys_menu` VALUES (127, '请假管理', 58, NULL, 1, 'C', 'leave', 'activiti/leave/index', NULL, 'tree-table', '0', 3, '0', 'admin', '2020-04-04 22:51:56', NULL, NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (128, '请假新增', 127, NULL, 1, 'F', NULL, '#', 'leave_add', NULL, '0', 1, '0', 'admin', '2020-04-04 22:52:44', NULL, NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (129, '请假修改', 127, NULL, 1, 'F', NULL, '#', 'leave_edit', NULL, '0', 2, '0', 'admin', '2020-04-04 22:53:01', NULL, NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (130, '请假删除', 127, NULL, 1, 'F', NULL, '#', 'leave_del', NULL, '0', 3, '0', 'admin', '2020-04-04 22:53:13', NULL, NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (131, '请假查看', 127, NULL, 1, 'F', NULL, '#', 'leave_view', NULL, '0', 4, '0', 'admin', '2020-04-04 22:53:26', NULL, NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (132, '请假导出', 127, NULL, 1, 'F', NULL, '#', 'leave_export', NULL, '0', 5, '0', 'admin', '2020-04-04 22:53:45', NULL, NULL, NULL, '0');
INSERT INTO `sys_menu` VALUES (133, '流程任务', 58, NULL, 1, 'C', 'task', 'activiti/task/index', NULL, 'checkbox', '0', 4, '0', 'admin', '2020-04-04 22:55:03', NULL, '2020-04-12 16:27:31', NULL, '0');
INSERT INTO `sys_menu` VALUES (134, '审批', 133, NULL, 1, 'F', NULL, '#', 'task_check', NULL, '0', 1, '0', 'admin', '2020-04-04 22:56:31', 'admin', '2020-04-12 16:27:33', NULL, '0');
INSERT INTO `sys_menu` VALUES (135, '批注', 133, NULL, 1, 'F', NULL, '#', 'task_comment', NULL, '0', 2, '0', 'admin', '2020-04-04 22:56:46', 'admin', '2020-04-12 16:19:54', NULL, '0');
INSERT INTO `sys_menu` VALUES (136, '流程追踪', 133, NULL, 1, 'F', NULL, '#', 'task_track', NULL, '0', 3, '0', 'admin', '2020-04-04 22:57:19', 'admin', '2020-04-05 13:04:51', NULL, '0');
INSERT INTO `sys_menu` VALUES (137, '任务查看', 133, NULL, 1, 'F', NULL, '#', 'task_view', NULL, '0', 4, '0', 'admin', '2020-04-04 22:57:19', 'admin', '2020-04-05 13:04:59', NULL, '0');
