(function webpackUniversalModuleDefinition(root, factory) {
	if(typeof exports === 'object' && typeof module === 'object')
		module.exports = factory(require("Vue"));
	else if(typeof define === 'function' && define.amd)
		define(["Vue"], factory);
	else if(typeof exports === 'object')
		exports["SelectTree"] = factory(require("Vue"));
	else
		root["SelectTree"] = factory(root["Vue"]);
})(typeof self !== 'undefined' ? self : this, function(__WEBPACK_EXTERNAL_MODULE_0__) {
return /******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "/";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 3);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports) {

module.exports = __WEBPACK_EXTERNAL_MODULE_0__;

/***/ }),
/* 1 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__util_js__ = __webpack_require__(6);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_throttle_debounce_debounce__ = __webpack_require__(7);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_throttle_debounce_debounce___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_throttle_debounce_debounce__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_element_ui_src_mixins_emitter__ = __webpack_require__(9);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_element_ui_src_mixins_focus__ = __webpack_require__(10);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_element_ui_src_utils_clickoutside__ = __webpack_require__(11);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_element_ui_src_utils_dom__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_element_ui_src_utils_resize_event__ = __webpack_require__(12);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_element_ui_src_utils_scroll_into_view__ = __webpack_require__(13);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//










var sizeMap = {
  'medium': 36,
  'small': 32,
  'mini': 28
};

/* harmony default export */ __webpack_exports__["a"] = ({
  mixins: [__WEBPACK_IMPORTED_MODULE_2_element_ui_src_mixins_emitter__["a" /* default */], Object(__WEBPACK_IMPORTED_MODULE_3_element_ui_src_mixins_focus__["a" /* default */])('reference')],

  name: 'selectTree',

  componentName: 'selectTree',

  inject: {
    elFormItem: {
      default: ''
    }
  },

  provide: function provide() {
    return {
      'select': this
    };
  },


  computed: {
    _elFormItemSize: function _elFormItemSize() {
      return (this.elFormItem || {}).elFormItemSize;
    },
    iconClass: function iconClass() {
      var criteria = this.clearable && !this.disabled && this.inputHovering && !this.multiple && this.value !== undefined && this.value !== '';
      return criteria ? 'circle-close is-show-close' : this.remote && this.filterable ? '' : 'arrow-up';
    },
    debounce: function debounce() {
      return this.remote ? 300 : 0;
    },
    emptyText: function emptyText() {
      if (this.loading) {
        return this.loadingText || '加载中';
      } else {
        if (this.remote && this.query === '' && this.treeData.length === 0) return false;
        if (this.filterable && this.query && this.treeData.length > 0 && this.$refs.tree2.$el.querySelector('.el-tree-node:not(.is-hidden)') === null) {
          return this.noMatchText || '无匹配数据';
        }
        if (this.treeData.length === 0) {
          return this.noDataText || '无数据';
        }
      }
      return null;
    },
    selectSize: function selectSize() {
      return this.size || this._elFormItemSize || (this.$ELEMENT || {}).size;
    },
    collapseTagSize: function collapseTagSize() {
      return ['small', 'mini'].indexOf(this.selectSize) > -1 ? 'mini' : 'small';
    },
    isQuote: function isQuote() {
      return {
        SHOW: 0,
        PRIMARY_KEY: 1,
        TEXTBOX: 2,
        PRIMARY_KEY_AND_SHOW: 3,
        NOT_SHOW: 4,
        PARENT_NODE: 5,
        DROPDOWN_SHOW: 6
      };
    },
    valueKey: function valueKey() {
      // for (const c of this.columnList) {
      //   if (
      //     c.isQuote === this.isQuote.PRIMARY_KEY ||
      //     c.isQuote === this.isQuote.PRIMARY_KEY_AND_SHOW
      //   ) {
      //     return c.col
      //   }
      // }
      return 'id';
    },
    labelKey: function labelKey() {
      // for (const c of this.columnList) {
      //   if (c.isQuote === this.isQuote.TEXTBOX || c.isQuote === this.isQuote.PRIMARY_KEY_AND_SHOW) {
      //     return c.col
      //   }
      // }
      return 'name';
    },
    defaultProps: function defaultProps() {
      return {
        children: 'children',
        label: this.labelKey
      };
    }
  },

  directives: { Clickoutside: __WEBPACK_IMPORTED_MODULE_4_element_ui_src_utils_clickoutside__["a" /* default */] },

  props: {
    name: String,
    id: String,
    value: {
      required: true
    },
    size: String,
    disabled: Boolean,
    clearable: Boolean,
    filterable: {
      type: Boolean,
      default: true
    },
    allowCreate: Boolean,
    loading: Boolean,
    popperClass: String,
    remote: {
      default: true
    },
    loadingText: String,
    noMatchText: String,
    noDataText: String,
    // remoteMethod: Function,
    // filterMethod: Function,
    multiple: Boolean,
    multipleLimit: {
      type: Number,
      default: 0
    },
    placeholder: {
      type: String,
      default: '请选择'
    },
    defaultFirstOption: Boolean,
    reserveKeyword: Boolean,
    // valueKey: {
    //   type: String,
    //   default: 'value'
    // },
    collapseTags: Boolean,
    // url: {
    //   type: String,
    //   required: true
    // },
    // method: {
    //   type: String,
    //   required: true
    // },
    // columnList: Array
    dataList: {
      type: Array,
      default: function _default() {
        return [];
      }
    }
  },

  data: function data() {
    return {
      // options: [],
      createdLabel: null,
      createdSelected: false,
      selected: this.multiple ? [] : {},
      inputLength: 20,
      inputWidth: 0,
      cachedPlaceHolder: '',
      // optionsCount: 0,
      filteredOptionsCount: 0,
      visible: false,
      selectedLabel: '',
      hoverIndex: -1,
      query: '',
      previousQuery: '',
      inputHovering: false,
      currentPlaceholder: '',
      // dataList: [],
      treeData: [],
      total: 0,
      listQuery: {
        limit: 20,
        page: 1
      }
    };
  },


  watch: {
    dataList: function dataList() {
      if (this.dataList) {
        this.treeData = Object(__WEBPACK_IMPORTED_MODULE_0__util_js__["a" /* convertTreeData */])(this.dataList);
        this.setSelected();
      }
    },
    query: function query(val) {
      this.$refs.tree2.filter(val);
    },
    disabled: function disabled() {
      var _this = this;

      this.$nextTick(function () {
        _this.resetInputHeight();
      });
    },
    placeholder: function placeholder(val) {
      this.cachedPlaceHolder = this.currentPlaceholder = val;
    },
    value: function value(val) {
      if (this.multiple) {
        this.resetInputHeight();
        if (val.length > 0 || this.$refs.input && this.query !== '') {
          this.currentPlaceholder = '';
        } else {
          this.currentPlaceholder = this.cachedPlaceHolder;
        }
        if (this.filterable && !this.reserveKeyword) {
          this.query = '';
          this.handleQueryChange(this.query);
        }
      }
      this.setSelected();
      if (this.filterable && !this.multiple) {
        this.inputLength = 20;
      }
    },
    visible: function visible(val) {
      var _this2 = this;

      if (!val) {
        this.$refs.reference.$el.querySelector('input').blur();
        this.handleIconHide();
        if (this.$refs.input) {
          this.$refs.input.blur();
        }
        this.query = '';
        this.selectedLabel = '';
        this.inputLength = 20;
        this.resetHoverIndex();
        this.$nextTick(function () {
          if (_this2.$refs.input && _this2.$refs.input.value === '' && _this2.selected.length === 0) {
            _this2.currentPlaceholder = _this2.cachedPlaceHolder;
          }
        });
        if (!this.multiple) {
          if (this.selected) {
            if (this.filterable && this.allowCreate && this.createdSelected && this.createdOption) {
              this.selectedLabel = this.createdLabel;
            } else {
              this.selectedLabel = this.selected[this.labelKey];
            }
            if (this.filterable) this.query = this.selectedLabel;
          }
        }
      } else {
        this.handleIconShow();
        if (this.filterable) {
          this.query = this.remote ? '' : this.selectedLabel;
          this.handleQueryChange(this.query);
          if (this.multiple) {
            this.$refs.input.focus();
          }
        }
      }
      this.$emit('visible-change', val);
    }
  },

  methods: {
    filterNode: function filterNode(value, data) {
      if (!value) return true;
      return data[this.labelKey].indexOf(value) !== -1 || data[this.valueKey].indexOf(value) !== -1;
    },
    remoteMethod: function remoteMethod(query) {
      if (this.dataList) {
        this.treeData = Object(__WEBPACK_IMPORTED_MODULE_0__util_js__["a" /* convertTreeData */])(this.dataList);
        this.setSelected();
      }
      // const data = Object.assign({}, this.filterProps, this.listQuery)
      // data.key = query
      // this.$http({
      //   url: this.url,
      //   method: this.method,
      //   params: (this.method === 'GET' || this.method === 'DELETE') ? data : null,
      //   data: (this.method === 'GET' || this.method === 'DELETE') ? null : data
      // })
      //   .then(backInfo => {
      //     this.loading = false
      //     this.dataList = backInfo.data.list
      //     this.treeData = convertTreeData(backInfo.data.list)
      //     this.total = Number(backInfo.data.total)
      //     this.setSelected()
      //   })
      //   .catch(err => {
      //     console.log(err)
      //     this.noMatchText = '搜索出错了'
      //   })
    },
    navigateRows: function navigateRows(direction) {
      var _this3 = this;

      if (!this.visible) {
        this.visible = true;
        return;
      }
      if (this.dataList.length === 0) return;
      if (direction === 'next') {
        this.hoverIndex++;
        if (this.hoverIndex >= this.dataList.length) {
          this.hoverIndex = 0;
        }
      }
      if (direction === 'prev') {
        this.hoverIndex--;
        if (this.hoverIndex < 0) {
          this.hoverIndex = this.dataList.length - 1;
        }
      }
      var row = Object(__WEBPACK_IMPORTED_MODULE_0__util_js__["c" /* getTreeNodeByIndex */])(this.treeData, this.hoverIndex);
      if (row === null) return;
      this.$refs.tree2.setCurrentKey(row[this.valueKey]);
      this.$nextTick(function () {
        return _this3.scrollToRow(row);
      });
    },
    selectRow: function selectRow() {
      var row = this.dataList[this.hoverIndex];
      if (row) {
        if (this.multiple) {
          this.handleRowSelect(row, !(this.value.indexOf(row[this.valueKey]) > -1));
        } else {
          this.handleRowSelect(row, true);
        }
      }
    },
    nodeClick: function nodeClick(data, node, tree) {
      if (!this.multiple) {
        this.handleRowSelect(data, true);
      }
    },
    checkChange: function checkChange(data, check, childCheck) {
      if (!this.multiple) return;
      this.handleRowSelect(data, check);
    },
    handleQueryChange: function handleQueryChange(val) {
      var _this4 = this;

      if (this.previousQuery === val) return;
      this.previousQuery = val;
      this.hoverIndex = -1;
      if (this.multiple && this.filterable) {
        var length = this.$refs.input.value.length * 15 + 20;
        this.inputLength = this.collapseTags ? Math.min(50, length) : length;
        this.managePlaceholder();
        this.resetInputHeight();
      }
      this.$nextTick(function () {
        _this4.filteredOptionsCount = _this4.$refs.tree2.$el.querySelectorAll('.el-tree-node:not(.is-hidden)').length;
      });
    },
    handleIconHide: function handleIconHide() {
      var icon = this.$el.querySelector('.el-input__icon');
      if (icon) {
        Object(__WEBPACK_IMPORTED_MODULE_5_element_ui_src_utils_dom__["d" /* removeClass */])(icon, 'is-reverse');
      }
    },
    handleIconShow: function handleIconShow() {
      var icon = this.$el.querySelector('.el-input__icon');
      if (icon && !Object(__WEBPACK_IMPORTED_MODULE_5_element_ui_src_utils_dom__["b" /* hasClass */])(icon, 'el-icon-circle-close')) {
        Object(__WEBPACK_IMPORTED_MODULE_5_element_ui_src_utils_dom__["a" /* addClass */])(icon, 'is-reverse');
      }
    },
    scrollToRow: function scrollToRow(rows) {
      var r = Array.isArray(rows) && rows[0] ? rows[0] : rows;
      var rIndex = r ? Object(__WEBPACK_IMPORTED_MODULE_0__util_js__["b" /* getTreeIndexByValue */])(this.treeData, r[this.valueKey], this.valueKey) : -1;
      if (this.$refs.tree2 && rIndex > -1) {
        var menu = this.$refs.tree2.$el;
        var target = menu.querySelectorAll('.el-tree-node')[rIndex];
        Object(__WEBPACK_IMPORTED_MODULE_7_element_ui_src_utils_scroll_into_view__["a" /* default */])(menu, target);
      }
    },
    handleMenuEnter: function handleMenuEnter() {
      var _this5 = this;

      this.$nextTick(function () {
        return _this5.scrollToRow(_this5.selected);
      });
    },
    emitChange: function emitChange(val) {
      if (!Object(__WEBPACK_IMPORTED_MODULE_0__util_js__["e" /* valueEquals */])(this.value, val)) {
        this.$emit('change', val);
      }
    },
    getRow: function getRow(v) {
      var result = [];
      var _iteratorNormalCompletion = true;
      var _didIteratorError = false;
      var _iteratorError = undefined;

      try {
        for (var _iterator = this.dataList[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
          var i = _step.value;

          if (i[this.valueKey] === v) {
            result.push(i);
          }
        }
      } catch (err) {
        _didIteratorError = true;
        _iteratorError = err;
      } finally {
        try {
          if (!_iteratorNormalCompletion && _iterator.return) {
            _iterator.return();
          }
        } finally {
          if (_didIteratorError) {
            throw _iteratorError;
          }
        }
      }

      return result;
    },
    setSelected: function setSelected() {
      var _this6 = this;

      if (!this.multiple) {
        var row = this.getRow(this.value)[0];
        if (row != null) {
          row.hitState = false;
          this.$refs.tree2.setCheckedKeys([this.value]);
          this.selectedLabel = row[this.labelKey];
          this.selected = row;
          if (this.filterable) this.query = this.selectedLabel;
        } else {
          this.selectedLabel = '';
          this.selected = null;
        }
        return;
      }
      var result = [];
      if (Array.isArray(this.value)) {
        this.value.forEach(function (v) {
          var r = _this6.getRow(v);
          if (r && r.length) {
            r.hitState = false;
            result.push(r[0]);
          }
        });
      }

      this.selected = result;
      this.$nextTick(function () {
        if (_this6.multiple) {
          _this6.$refs.tree2.setCheckedKeys(_this6.value);
        }
        _this6.resetInputHeight();
      });
    },
    handleFocus: function handleFocus(event) {
      this.visible = true;
      this.$emit('focus', event);
    },
    handleBlur: function handleBlur(event) {
      this.$emit('blur', event);
    },
    handleIconClick: function handleIconClick(event) {
      if (this.iconClass.indexOf('circle-close') > -1) {
        this.deleteSelected(event);
      } else {
        this.toggleMenu();
      }
    },
    handleMouseDown: function handleMouseDown(event) {
      if (event.target.tagName !== 'INPUT') return;
      if (this.visible) {
        this.handleClose();
        event.preventDefault();
      }
    },
    doDestroy: function doDestroy() {
      this.$refs.popper && this.$refs.popper.doDestroy();
    },
    handleClose: function handleClose() {
      this.visible = false;
    },
    toggleLastOptionHitState: function toggleLastOptionHitState(hit) {
      if (!Array.isArray(this.selected)) return;
      var option = this.selected[this.selected.length - 1];
      if (!option) return;

      if (hit === true || hit === false) {
        option.hitState = hit;
        return hit;
      }

      option.hitState = !option.hitState;
      return option.hitState;
    },
    deletePrevTag: function deletePrevTag(e) {
      if (e.target.value.length <= 0 && !this.toggleLastOptionHitState()) {
        var value = this.value.slice();
        value.pop();
        this.$emit('input', value);
        this.emitChange(value);
      }
    },
    managePlaceholder: function managePlaceholder() {
      if (this.currentPlaceholder !== '') {
        this.currentPlaceholder = this.$refs.input.value ? '' : this.cachedPlaceHolder;
      }
    },
    resetInputState: function resetInputState(e) {
      if (e.keyCode !== 8) this.toggleLastOptionHitState(false);
      this.inputLength = this.$refs.input.value.length * 15 + 20;
      this.resetInputHeight();
    },
    resetInputHeight: function resetInputHeight() {
      var _this7 = this;

      if (this.collapseTags && !this.filterable) return;
      this.$nextTick(function () {
        if (!_this7.$refs.reference) return;
        var inputChildNodes = _this7.$refs.reference.$el.childNodes;
        var input = [].filter.call(inputChildNodes, function (item) {
          return item.tagName === 'INPUT';
        })[0];
        var tags = _this7.$refs.tags;
        input.style.height = _this7.selected.length === 0 ? (sizeMap[_this7.selectSize] || 40) + 'px' : Math.max(tags ? tags.clientHeight + 10 : 0, sizeMap[_this7.selectSize] || 40) + 'px';
      });
    },
    resetHoverIndex: function resetHoverIndex() {
      var _this8 = this;

      setTimeout(function () {
        if (!_this8.multiple) {
          _this8.hoverIndex = Object(__WEBPACK_IMPORTED_MODULE_0__util_js__["b" /* getTreeIndexByValue */])(_this8.treeData, _this8.selected, _this8.valueKey);
        } else {
          if (_this8.selected.length > 0) {
            _this8.hoverIndex = Math.min.apply(null, _this8.selected.map(function (item) {
              return Object(__WEBPACK_IMPORTED_MODULE_0__util_js__["b" /* getTreeIndexByValue */])(_this8.treeData, item, _this8.valueKey);
            }));
          } else {
            _this8.hoverIndex = -1;
          }
        }
      }, 300);
    },
    handleRowSelect: function handleRowSelect(row, check) {
      var _this9 = this;

      if (this.multiple) {
        var value = this.value.slice();
        var rowIndex = this.getValueIndex(value, row[this.valueKey]);
        if (rowIndex > -1) {
          value.splice(rowIndex, 1);
        } else if (check && (this.multipleLimit <= 0 || value.length < this.multipleLimit)) {
          value.push(row[this.valueKey]);
        }
        this.$emit('input', value);
        this.emitChange(value);
        if (row.created) {
          this.query = '';
          this.handleQueryChange('');
          this.inputLength = 20;
        }
        if (this.filterable) this.$refs.input.focus();
      } else {
        this.$emit('input', row[this.valueKey]);
        this.emitChange(row[this.valueKey]);
        this.visible = false;
      }
      this.$nextTick(function () {
        return _this9.scrollToRow(row);
      });
    },
    getValueIndex: function getValueIndex() {
      var arr = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : [];
      var value = arguments[1];

      var isObject = Object.prototype.toString.call(value).toLowerCase() === '[object object]';
      if (!isObject) {
        return arr.indexOf(value);
      } else {
        var valueKey = this.valueKey;
        var index = -1;
        arr.some(function (item, i) {
          if (Object(__WEBPACK_IMPORTED_MODULE_0__util_js__["d" /* getValueByPath */])(item, valueKey) === Object(__WEBPACK_IMPORTED_MODULE_0__util_js__["d" /* getValueByPath */])(value, valueKey)) {
            index = i;
            return true;
          }
          return false;
        });
        return index;
      }
    },
    toggleMenu: function toggleMenu() {
      if (!this.disabled) {
        this.visible = !this.visible;
        if (this.visible) {
          (this.$refs.input || this.$refs.reference).focus();
        }
      }
    },
    deleteSelected: function deleteSelected(event) {
      event.stopPropagation();
      this.$emit('input', '');
      this.emitChange('');
      this.visible = false;
      this.$emit('clear');
    },
    deleteTag: function deleteTag(event, tag) {
      var index = this.selected.indexOf(tag);
      if (index > -1 && !this.disabled) {
        var value = this.value.slice();
        value.splice(index, 1);
        this.$emit('input', value);
        this.emitChange(value);
        this.$emit('remove-tag', tag.value);
      }
      event.stopPropagation();
    },
    onInputChange: function onInputChange() {
      if (this.filterable && this.query !== this.selectedLabel) {
        this.query = this.selectedLabel;
        this.handleQueryChange(this.query);
      }
    },
    resetInputWidth: function resetInputWidth() {
      this.inputWidth = this.$refs.reference.$el.getBoundingClientRect().width;
    },
    handleResize: function handleResize() {
      this.resetInputWidth();
      if (this.multiple) this.resetInputHeight();
    },
    getValueKey: function getValueKey(item) {
      var v = item[this.valueKey];
      if (Object.prototype.toString.call(v).toLowerCase() !== '[object object]') {
        return v;
      } else {
        return Object(__WEBPACK_IMPORTED_MODULE_0__util_js__["d" /* getValueByPath */])(v, this.valueKey);
      }
    }
  },

  created: function created() {
    var _this10 = this;

    this.cachedPlaceHolder = this.currentPlaceholder = this.placeholder;
    if (this.multiple && !Array.isArray(this.value)) {
      this.$emit('input', []);
    }
    if (!this.multiple && Array.isArray(this.value)) {
      this.$emit('input', '');
    }

    this.debouncedOnInputChange = __WEBPACK_IMPORTED_MODULE_1_throttle_debounce_debounce___default()(this.debounce, function () {
      _this10.onInputChange();
    });

    this.$on('setSelected', this.setSelected);
  },
  mounted: function mounted() {
    var _this11 = this;

    if (this.multiple && Array.isArray(this.value) && this.value.length > 0) {
      this.currentPlaceholder = '';
    }
    Object(__WEBPACK_IMPORTED_MODULE_6_element_ui_src_utils_resize_event__["a" /* addResizeListener */])(this.$el, this.handleResize);
    if (this.remote && this.multiple) {
      this.resetInputHeight();
    }
    this.$nextTick(function () {
      if (_this11.$refs.reference && _this11.$refs.reference.$el) {
        _this11.inputWidth = _this11.$refs.reference.$el.getBoundingClientRect().width;
      }
    });
    this.remoteMethod(this.query);
    // this.setSelected()
  },
  beforeDestroy: function beforeDestroy() {
    if (this.$el && this.handleResize) Object(__WEBPACK_IMPORTED_MODULE_6_element_ui_src_utils_resize_event__["b" /* removeResizeListener */])(this.$el, this.handleResize);
  }
});

/***/ }),
/* 2 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "c", function() { return on; });
/* unused harmony export off */
/* unused harmony export once */
/* harmony export (immutable) */ __webpack_exports__["b"] = hasClass;
/* harmony export (immutable) */ __webpack_exports__["a"] = addClass;
/* harmony export (immutable) */ __webpack_exports__["d"] = removeClass;
/* unused harmony export getStyle */
/* unused harmony export setStyle */
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_vue__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_vue__);
var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

/* istanbul ignore next */



var isServer = __WEBPACK_IMPORTED_MODULE_0_vue___default.a.prototype.$isServer;
var SPECIAL_CHARS_REGEXP = /([\:\-\_]+(.))/g;
var MOZ_HACK_REGEXP = /^moz([A-Z])/;
var ieVersion = isServer ? 0 : Number(document.documentMode);

/* istanbul ignore next */
var trim = function trim(string) {
  return (string || '').replace(/^[\s\uFEFF]+|[\s\uFEFF]+$/g, '');
};
/* istanbul ignore next */
var camelCase = function camelCase(name) {
  return name.replace(SPECIAL_CHARS_REGEXP, function (_, separator, letter, offset) {
    return offset ? letter.toUpperCase() : letter;
  }).replace(MOZ_HACK_REGEXP, 'Moz$1');
};

/* istanbul ignore next */
var on = function () {
  if (!isServer && document.addEventListener) {
    return function (element, event, handler) {
      if (element && event && handler) {
        element.addEventListener(event, handler, false);
      }
    };
  } else {
    return function (element, event, handler) {
      if (element && event && handler) {
        element.attachEvent('on' + event, handler);
      }
    };
  }
}();

/* istanbul ignore next */
var off = function () {
  if (!isServer && document.removeEventListener) {
    return function (element, event, handler) {
      if (element && event) {
        element.removeEventListener(event, handler, false);
      }
    };
  } else {
    return function (element, event, handler) {
      if (element && event) {
        element.detachEvent('on' + event, handler);
      }
    };
  }
}();

/* istanbul ignore next */
var once = function once(el, event, fn) {
  var listener = function listener() {
    if (fn) {
      fn.apply(this, arguments);
    }
    off(el, event, listener);
  };
  on(el, event, listener);
};

/* istanbul ignore next */
function hasClass(el, cls) {
  if (!el || !cls) return false;
  if (cls.indexOf(' ') !== -1) throw new Error('className should not contain space.');
  if (el.classList) {
    return el.classList.contains(cls);
  } else {
    return (' ' + el.className + ' ').indexOf(' ' + cls + ' ') > -1;
  }
};

/* istanbul ignore next */
function addClass(el, cls) {
  if (!el) return;
  var curClass = el.className;
  var classes = (cls || '').split(' ');

  for (var i = 0, j = classes.length; i < j; i++) {
    var clsName = classes[i];
    if (!clsName) continue;

    if (el.classList) {
      el.classList.add(clsName);
    } else if (!hasClass(el, clsName)) {
      curClass += ' ' + clsName;
    }
  }
  if (!el.classList) {
    el.className = curClass;
  }
};

/* istanbul ignore next */
function removeClass(el, cls) {
  if (!el || !cls) return;
  var classes = cls.split(' ');
  var curClass = ' ' + el.className + ' ';

  for (var i = 0, j = classes.length; i < j; i++) {
    var clsName = classes[i];
    if (!clsName) continue;

    if (el.classList) {
      el.classList.remove(clsName);
    } else if (hasClass(el, clsName)) {
      curClass = curClass.replace(' ' + clsName + ' ', ' ');
    }
  }
  if (!el.classList) {
    el.className = trim(curClass);
  }
};

/* istanbul ignore next */
var getStyle = ieVersion < 9 ? function (element, styleName) {
  if (isServer) return;
  if (!element || !styleName) return null;
  styleName = camelCase(styleName);
  if (styleName === 'float') {
    styleName = 'styleFloat';
  }
  try {
    switch (styleName) {
      case 'opacity':
        try {
          return element.filters.item('alpha').opacity / 100;
        } catch (e) {
          return 1.0;
        }
      default:
        return element.style[styleName] || element.currentStyle ? element.currentStyle[styleName] : null;
    }
  } catch (e) {
    return element.style[styleName];
  }
} : function (element, styleName) {
  if (isServer) return;
  if (!element || !styleName) return null;
  styleName = camelCase(styleName);
  if (styleName === 'float') {
    styleName = 'cssFloat';
  }
  try {
    var computed = document.defaultView.getComputedStyle(element, '');
    return element.style[styleName] || computed ? computed[styleName] : null;
  } catch (e) {
    return element.style[styleName];
  }
};

/* istanbul ignore next */
function setStyle(element, styleName, value) {
  if (!element || !styleName) return;

  if ((typeof styleName === 'undefined' ? 'undefined' : _typeof(styleName)) === 'object') {
    for (var prop in styleName) {
      if (styleName.hasOwnProperty(prop)) {
        setStyle(element, prop, styleName[prop]);
      }
    }
  } else {
    styleName = camelCase(styleName);
    if (styleName === 'opacity' && ieVersion < 9) {
      element.style.filter = isNaN(value) ? '' : 'alpha(opacity=' + value * 100 + ')';
    } else {
      element.style[styleName] = value;
    }
  }
};

/***/ }),
/* 3 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__components_selectTree_vue__ = __webpack_require__(4);

/* harmony default export */ __webpack_exports__["default"] = (__WEBPACK_IMPORTED_MODULE_0__components_selectTree_vue__["a" /* default */]);

// import simple from './components/simple.vue'
// export default simple

/***/ }),
/* 4 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_selectTree_vue__ = __webpack_require__(1);
/* unused harmony namespace reexport */
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_8656603a_hasScoped_false_transformToRequire_video_src_poster_source_src_img_src_image_xlink_href_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_selectTree_vue__ = __webpack_require__(14);
var normalizeComponent = __webpack_require__(5)
/* script */


/* template */

/* template functional */
var __vue_template_functional__ = false
/* styles */
var __vue_styles__ = null
/* scopeId */
var __vue_scopeId__ = null
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_selectTree_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_8656603a_hasScoped_false_transformToRequire_video_src_poster_source_src_img_src_image_xlink_href_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_selectTree_vue__["a" /* default */],
  __vue_template_functional__,
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),
/* 5 */
/***/ (function(module, exports) {

/* globals __VUE_SSR_CONTEXT__ */

// IMPORTANT: Do NOT use ES2015 features in this file.
// This module is a runtime utility for cleaner component module output and will
// be included in the final webpack user bundle.

module.exports = function normalizeComponent (
  rawScriptExports,
  compiledTemplate,
  functionalTemplate,
  injectStyles,
  scopeId,
  moduleIdentifier /* server only */
) {
  var esModule
  var scriptExports = rawScriptExports = rawScriptExports || {}

  // ES6 modules interop
  var type = typeof rawScriptExports.default
  if (type === 'object' || type === 'function') {
    esModule = rawScriptExports
    scriptExports = rawScriptExports.default
  }

  // Vue.extend constructor export interop
  var options = typeof scriptExports === 'function'
    ? scriptExports.options
    : scriptExports

  // render functions
  if (compiledTemplate) {
    options.render = compiledTemplate.render
    options.staticRenderFns = compiledTemplate.staticRenderFns
    options._compiled = true
  }

  // functional template
  if (functionalTemplate) {
    options.functional = true
  }

  // scopedId
  if (scopeId) {
    options._scopeId = scopeId
  }

  var hook
  if (moduleIdentifier) { // server build
    hook = function (context) {
      // 2.3 injection
      context =
        context || // cached call
        (this.$vnode && this.$vnode.ssrContext) || // stateful
        (this.parent && this.parent.$vnode && this.parent.$vnode.ssrContext) // functional
      // 2.2 with runInNewContext: true
      if (!context && typeof __VUE_SSR_CONTEXT__ !== 'undefined') {
        context = __VUE_SSR_CONTEXT__
      }
      // inject component styles
      if (injectStyles) {
        injectStyles.call(this, context)
      }
      // register component module identifier for async chunk inferrence
      if (context && context._registeredComponents) {
        context._registeredComponents.add(moduleIdentifier)
      }
    }
    // used by ssr in case component is cached and beforeCreate
    // never gets called
    options._ssrRegister = hook
  } else if (injectStyles) {
    hook = injectStyles
  }

  if (hook) {
    var functional = options.functional
    var existing = functional
      ? options.render
      : options.beforeCreate

    if (!functional) {
      // inject component registration as beforeCreate hook
      options.beforeCreate = existing
        ? [].concat(existing, hook)
        : [hook]
    } else {
      // for template-only hot-reload because in that case the render fn doesn't
      // go through the normalizer
      options._injectStyles = hook
      // register for functioal component in vue file
      options.render = function renderWithStyleInjection (h, context) {
        hook.call(context)
        return existing(h, context)
      }
    }
  }

  return {
    esModule: esModule,
    exports: scriptExports,
    options: options
  }
}


/***/ }),
/* 6 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (immutable) */ __webpack_exports__["a"] = convertTreeData;
/* harmony export (immutable) */ __webpack_exports__["c"] = getTreeNodeByIndex;
/* harmony export (immutable) */ __webpack_exports__["b"] = getTreeIndexByValue;
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "e", function() { return valueEquals; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "d", function() { return getValueByPath; });
/**
 * 将具有父子关系的数据转化成树形结构
 * @param {array} rows 行结构数据
 * @param {object} attributes 属性信息 { key: "id", parentKey: "pid", childKey: "children"  }
 */
function convertTreeData(sNodes, attributes) {
  var i = void 0,
      l = void 0;
  var attr = Object.assign({
    key: 'id',
    parentKey: 'pid',
    childKey: 'children'
  });
  var key = attr.key,
      parentKey = attr.parentKey,
      childKey = attr.childKey;

  var r = [];
  var tmpMap = {};
  for (i = 0, l = sNodes.length; i < l; i++) {
    tmpMap[sNodes[i][key]] = sNodes[i];
  }
  for (i = 0, l = sNodes.length; i < l; i++) {
    if (tmpMap[sNodes[i][parentKey]] && sNodes[i][key] !== sNodes[i][parentKey]) {
      if (!tmpMap[sNodes[i][parentKey]][childKey]) {
        tmpMap[sNodes[i][parentKey]][childKey] = [];
      }
      tmpMap[sNodes[i][parentKey]][childKey].push(sNodes[i]);
    } else {
      r.push(sNodes[i]);
    }
  }
  return r;
}

function getTreeNodeByIndex2(nodeList, tempCount, index) {
  var _iteratorNormalCompletion = true;
  var _didIteratorError = false;
  var _iteratorError = undefined;

  try {
    for (var _iterator = nodeList[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
      var node = _step.value;

      if (tempCount.i === index) {
        return node;
      }
      tempCount.i++;
      if (node.children && node.children.length > 0) {
        var temp = getTreeNodeByIndex2(node.children, tempCount, index);
        if (temp != null) {
          return temp;
        }
      }
    }
  } catch (err) {
    _didIteratorError = true;
    _iteratorError = err;
  } finally {
    try {
      if (!_iteratorNormalCompletion && _iterator.return) {
        _iterator.return();
      }
    } finally {
      if (_didIteratorError) {
        throw _iteratorError;
      }
    }
  }

  return null;
}

/**
 * 根据索引号获取树节点
 * @param {Array} nodeList 树形数据
 * @param {*} index 索引号
 */
function getTreeNodeByIndex(nodeList, index) {
  var tempCount = { i: 0 };
  return getTreeNodeByIndex2(nodeList, tempCount, index);
}

function getTreeIndexByValue2(nodeList, tempCount, value, key) {
  var _iteratorNormalCompletion2 = true;
  var _didIteratorError2 = false;
  var _iteratorError2 = undefined;

  try {
    for (var _iterator2 = nodeList[Symbol.iterator](), _step2; !(_iteratorNormalCompletion2 = (_step2 = _iterator2.next()).done); _iteratorNormalCompletion2 = true) {
      var node = _step2.value;

      if (node[key] === value) {
        return tempCount.i;
      }
      tempCount.i++;
      if (node.children && node.children.length > 0) {
        var temp = getTreeIndexByValue2(node.children, tempCount, value, key);
        if (temp !== -1) {
          return tempCount.i;
        }
      }
    }
  } catch (err) {
    _didIteratorError2 = true;
    _iteratorError2 = err;
  } finally {
    try {
      if (!_iteratorNormalCompletion2 && _iterator2.return) {
        _iterator2.return();
      }
    } finally {
      if (_didIteratorError2) {
        throw _iteratorError2;
      }
    }
  }

  return -1;
}

/**
 * 根据键值获取对应的索引号
 * @param {*} nodeList 树形数据
 * @param {*} value 值
 * @param {*} key 键名
 */
function getTreeIndexByValue(nodeList, value, key) {
  var tempCount = { i: 0 };
  return getTreeIndexByValue2(nodeList, tempCount, value, key);
}

function getParentId(arr, pid, list, attr) {
  if (arr) {
    for (var i = 0; i < arr.length; i++) {
      if (arr[i][attr.idKey] === pid) {
        list.push(arr[i][attr.pKey]);
        return true;
      } else {
        var t = getParentId(arr[i][attr.cKey], pid, list, attr);
        if (t) {
          list.push(arr[i][attr.pKey]);
          return true;
        }
      }
    }
  }
  return false;
}

var valueEquals = function valueEquals(a, b) {
  // see: https://stackoverflow.com/questions/3115982/how-to-check-if-two-arrays-are-equal-with-javascript
  if (a === b) return true;
  if (!(a instanceof Array)) return false;
  if (!(b instanceof Array)) return false;
  if (a.length !== b.length) return false;
  for (var i = 0; i !== a.length; ++i) {
    if (a[i] !== b[i]) return false;
  }
  return true;
};

var getValueByPath = function getValueByPath(object, prop) {
  prop = prop || '';
  var paths = prop.split('.');
  var current = object;
  var result = null;
  for (var i = 0, j = paths.length; i < j; i++) {
    var path = paths[i];
    if (!current) break;

    if (i === j - 1) {
      result = current[path];
      break;
    }
    current = current[path];
  }
  return result;
};

/***/ }),
/* 7 */
/***/ (function(module, exports, __webpack_require__) {

/* eslint-disable no-undefined */

var throttle = __webpack_require__(8);

/**
 * Debounce execution of a function. Debouncing, unlike throttling,
 * guarantees that a function is only executed a single time, either at the
 * very beginning of a series of calls, or at the very end.
 *
 * @param  {Number}   delay         A zero-or-greater delay in milliseconds. For event callbacks, values around 100 or 250 (or even higher) are most useful.
 * @param  {Boolean}  atBegin       Optional, defaults to false. If atBegin is false or unspecified, callback will only be executed `delay` milliseconds
 *                                  after the last debounced-function call. If atBegin is true, callback will be executed only at the first debounced-function call.
 *                                  (After the throttled-function has not been called for `delay` milliseconds, the internal counter is reset).
 * @param  {Function} callback      A function to be executed after delay milliseconds. The `this` context and all arguments are passed through, as-is,
 *                                  to `callback` when the debounced-function is executed.
 *
 * @return {Function} A new, debounced function.
 */
module.exports = function (delay, atBegin, callback) {
  return callback === undefined ? throttle(delay, atBegin, false) : throttle(delay, callback, atBegin !== false);
};

/***/ }),
/* 8 */
/***/ (function(module, exports) {

/* eslint-disable no-undefined,no-param-reassign,no-shadow */

/**
 * Throttle execution of a function. Especially useful for rate limiting
 * execution of handlers on events like resize and scroll.
 *
 * @param  {Number}    delay          A zero-or-greater delay in milliseconds. For event callbacks, values around 100 or 250 (or even higher) are most useful.
 * @param  {Boolean}   noTrailing     Optional, defaults to false. If noTrailing is true, callback will only execute every `delay` milliseconds while the
 *                                    throttled-function is being called. If noTrailing is false or unspecified, callback will be executed one final time
 *                                    after the last throttled-function call. (After the throttled-function has not been called for `delay` milliseconds,
 *                                    the internal counter is reset)
 * @param  {Function}  callback       A function to be executed after delay milliseconds. The `this` context and all arguments are passed through, as-is,
 *                                    to `callback` when the throttled-function is executed.
 * @param  {Boolean}   debounceMode   If `debounceMode` is true (at begin), schedule `clear` to execute after `delay` ms. If `debounceMode` is false (at end),
 *                                    schedule `callback` to execute after `delay` ms.
 *
 * @return {Function}  A new, throttled, function.
 */
module.exports = function (delay, noTrailing, callback, debounceMode) {

	// After wrapper has stopped being called, this timeout ensures that
	// `callback` is executed at the proper times in `throttle` and `end`
	// debounce modes.
	var timeoutID;

	// Keep track of the last time `callback` was executed.
	var lastExec = 0;

	// `noTrailing` defaults to falsy.
	if (typeof noTrailing !== 'boolean') {
		debounceMode = callback;
		callback = noTrailing;
		noTrailing = undefined;
	}

	// The `wrapper` function encapsulates all of the throttling / debouncing
	// functionality and when executed will limit the rate at which `callback`
	// is executed.
	function wrapper() {

		var self = this;
		var elapsed = Number(new Date()) - lastExec;
		var args = arguments;

		// Execute `callback` and update the `lastExec` timestamp.
		function exec() {
			lastExec = Number(new Date());
			callback.apply(self, args);
		}

		// If `debounceMode` is true (at begin) this is used to clear the flag
		// to allow future `callback` executions.
		function clear() {
			timeoutID = undefined;
		}

		if (debounceMode && !timeoutID) {
			// Since `wrapper` is being called for the first time and
			// `debounceMode` is true (at begin), execute `callback`.
			exec();
		}

		// Clear any existing timeout.
		if (timeoutID) {
			clearTimeout(timeoutID);
		}

		if (debounceMode === undefined && elapsed > delay) {
			// In throttle mode, if `delay` time has been exceeded, execute
			// `callback`.
			exec();
		} else if (noTrailing !== true) {
			// In trailing throttle mode, since `delay` time has not been
			// exceeded, schedule `callback` to execute `delay` ms after most
			// recent execution.
			//
			// If `debounceMode` is true (at begin), schedule `clear` to execute
			// after `delay` ms.
			//
			// If `debounceMode` is false (at end), schedule `callback` to
			// execute after `delay` ms.
			timeoutID = setTimeout(debounceMode ? clear : exec, debounceMode === undefined ? delay - elapsed : delay);
		}
	}

	// Return the wrapper function.
	return wrapper;
};

/***/ }),
/* 9 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
function _broadcast(componentName, eventName, params) {
  this.$children.forEach(function (child) {
    var name = child.$options.componentName;

    if (name === componentName) {
      child.$emit.apply(child, [eventName].concat(params));
    } else {
      _broadcast.apply(child, [componentName, eventName].concat([params]));
    }
  });
}
/* harmony default export */ __webpack_exports__["a"] = ({
  methods: {
    dispatch: function dispatch(componentName, eventName, params) {
      var parent = this.$parent || this.$root;
      var name = parent.$options.componentName;

      while (parent && (!name || name !== componentName)) {
        parent = parent.$parent;

        if (parent) {
          name = parent.$options.componentName;
        }
      }
      if (parent) {
        parent.$emit.apply(parent, [eventName].concat(params));
      }
    },
    broadcast: function broadcast(componentName, eventName, params) {
      _broadcast.call(this, componentName, eventName, params);
    }
  }
});

/***/ }),
/* 10 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony default export */ __webpack_exports__["a"] = (function (ref) {
  return {
    methods: {
      focus: function focus() {
        this.$refs[ref].focus();
      }
    }
  };
});;

/***/ }),
/* 11 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_vue__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_vue__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_element_ui_src_utils_dom__ = __webpack_require__(2);



var nodeList = [];
var ctx = '@@clickoutsideContext';

var startClick = void 0;
var seed = 0;

!__WEBPACK_IMPORTED_MODULE_0_vue___default.a.prototype.$isServer && Object(__WEBPACK_IMPORTED_MODULE_1_element_ui_src_utils_dom__["c" /* on */])(document, 'mousedown', function (e) {
  return startClick = e;
});

!__WEBPACK_IMPORTED_MODULE_0_vue___default.a.prototype.$isServer && Object(__WEBPACK_IMPORTED_MODULE_1_element_ui_src_utils_dom__["c" /* on */])(document, 'mouseup', function (e) {
  nodeList.forEach(function (node) {
    return node[ctx].documentHandler(e, startClick);
  });
});

function createDocumentHandler(el, binding, vnode) {
  return function () {
    var mouseup = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {};
    var mousedown = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};

    if (!vnode || !vnode.context || !mouseup.target || !mousedown.target || el.contains(mouseup.target) || el.contains(mousedown.target) || el === mouseup.target || vnode.context.popperElm && (vnode.context.popperElm.contains(mouseup.target) || vnode.context.popperElm.contains(mousedown.target))) return;

    if (binding.expression && el[ctx].methodName && vnode.context[el[ctx].methodName]) {
      vnode.context[el[ctx].methodName]();
    } else {
      el[ctx].bindingFn && el[ctx].bindingFn();
    }
  };
}

/**
 * v-clickoutside
 * @desc 点击元素外面才会触发的事件
 * @example
 * ```vue
 * <div v-element-clickoutside="handleClose">
 * ```
 */
/* harmony default export */ __webpack_exports__["a"] = ({
  bind: function bind(el, binding, vnode) {
    nodeList.push(el);
    var id = seed++;
    el[ctx] = {
      id: id,
      documentHandler: createDocumentHandler(el, binding, vnode),
      methodName: binding.expression,
      bindingFn: binding.value
    };
  },
  update: function update(el, binding, vnode) {
    el[ctx].documentHandler = createDocumentHandler(el, binding, vnode);
    el[ctx].methodName = binding.expression;
    el[ctx].bindingFn = binding.value;
  },
  unbind: function unbind(el) {
    var len = nodeList.length;

    for (var i = 0; i < len; i++) {
      if (nodeList[i][ctx].id === el[ctx].id) {
        nodeList.splice(i, 1);
        break;
      }
    }
    delete el[ctx];
  }
});

/***/ }),
/* 12 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return addResizeListener; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return removeResizeListener; });
/* Modified from https://github.com/sdecima/javascript-detect-element-resize
 * version: 0.5.3
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Sebastián Décima
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
var isServer = typeof window === 'undefined';

/* istanbul ignore next */
var requestFrame = function () {
  if (isServer) return;
  var raf = window.requestAnimationFrame || window.mozRequestAnimationFrame || window.webkitRequestAnimationFrame || function (fn) {
    return window.setTimeout(fn, 20);
  };
  return function (fn) {
    return raf(fn);
  };
}();

/* istanbul ignore next */
var cancelFrame = function () {
  if (isServer) return;
  var cancel = window.cancelAnimationFrame || window.mozCancelAnimationFrame || window.webkitCancelAnimationFrame || window.clearTimeout;
  return function (id) {
    return cancel(id);
  };
}();

/* istanbul ignore next */
var resetTrigger = function resetTrigger(element) {
  var trigger = element.__resizeTrigger__;
  var expand = trigger.firstElementChild;
  var contract = trigger.lastElementChild;
  var expandChild = expand.firstElementChild;

  contract.scrollLeft = contract.scrollWidth;
  contract.scrollTop = contract.scrollHeight;
  expandChild.style.width = expand.offsetWidth + 1 + 'px';
  expandChild.style.height = expand.offsetHeight + 1 + 'px';
  expand.scrollLeft = expand.scrollWidth;
  expand.scrollTop = expand.scrollHeight;
};

/* istanbul ignore next */
var checkTriggers = function checkTriggers(element) {
  return element.offsetWidth !== element.__resizeLast__.width || element.offsetHeight !== element.__resizeLast__.height;
};

/* istanbul ignore next */
var scrollListener = function scrollListener(event) {
  var _this = this;

  resetTrigger(this);
  if (this.__resizeRAF__) cancelFrame(this.__resizeRAF__);
  this.__resizeRAF__ = requestFrame(function () {
    if (checkTriggers(_this)) {
      _this.__resizeLast__.width = _this.offsetWidth;
      _this.__resizeLast__.height = _this.offsetHeight;
      _this.__resizeListeners__.forEach(function (fn) {
        fn.call(_this, event);
      });
    }
  });
};

/* Detect CSS Animations support to detect element display/re-attach */
var attachEvent = isServer ? {} : document.attachEvent;
var DOM_PREFIXES = 'Webkit Moz O ms'.split(' ');
var START_EVENTS = 'webkitAnimationStart animationstart oAnimationStart MSAnimationStart'.split(' ');
var RESIZE_ANIMATION_NAME = 'resizeanim';
var animation = false;
var keyFramePrefix = '';
var animationStartEvent = 'animationstart';

/* istanbul ignore next */
if (!attachEvent && !isServer) {
  var testElement = document.createElement('fakeelement');
  if (testElement.style.animationName !== undefined) {
    animation = true;
  }

  if (animation === false) {
    var prefix = '';
    for (var i = 0; i < DOM_PREFIXES.length; i++) {
      if (testElement.style[DOM_PREFIXES[i] + 'AnimationName'] !== undefined) {
        prefix = DOM_PREFIXES[i];
        keyFramePrefix = '-' + prefix.toLowerCase() + '-';
        animationStartEvent = START_EVENTS[i];
        animation = true;
        break;
      }
    }
  }
}

var stylesCreated = false;
/* istanbul ignore next */
var createStyles = function createStyles() {
  if (!stylesCreated && !isServer) {
    var animationKeyframes = '@' + keyFramePrefix + 'keyframes ' + RESIZE_ANIMATION_NAME + ' { from { opacity: 0; } to { opacity: 0; } } ';
    var animationStyle = keyFramePrefix + 'animation: 1ms ' + RESIZE_ANIMATION_NAME + ';';

    // opacity: 0 works around a chrome bug https://code.google.com/p/chromium/issues/detail?id=286360
    var css = animationKeyframes + '\n      .resize-triggers { ' + animationStyle + ' visibility: hidden; opacity: 0; }\n      .resize-triggers, .resize-triggers > div, .contract-trigger:before { content: " "; display: block; position: absolute; top: 0; left: 0; height: 100%; width: 100%; overflow: hidden; z-index: -1 }\n      .resize-triggers > div { background: #eee; overflow: auto; }\n      .contract-trigger:before { width: 200%; height: 200%; }';

    var head = document.head || document.getElementsByTagName('head')[0];
    var style = document.createElement('style');

    style.type = 'text/css';
    if (style.styleSheet) {
      style.styleSheet.cssText = css;
    } else {
      style.appendChild(document.createTextNode(css));
    }

    head.appendChild(style);
    stylesCreated = true;
  }
};

/* istanbul ignore next */
var addResizeListener = function addResizeListener(element, fn) {
  if (isServer) return;
  if (attachEvent) {
    element.attachEvent('onresize', fn);
  } else {
    if (!element.__resizeTrigger__) {
      if (getComputedStyle(element).position === 'static') {
        element.style.position = 'relative';
      }
      createStyles();
      element.__resizeLast__ = {};
      element.__resizeListeners__ = [];

      var resizeTrigger = element.__resizeTrigger__ = document.createElement('div');
      resizeTrigger.className = 'resize-triggers';
      resizeTrigger.innerHTML = '<div class="expand-trigger"><div></div></div><div class="contract-trigger"></div>';
      element.appendChild(resizeTrigger);

      resetTrigger(element);
      element.addEventListener('scroll', scrollListener, true);

      /* Listen for a css animation to detect element display/re-attach */
      if (animationStartEvent) {
        resizeTrigger.addEventListener(animationStartEvent, function (event) {
          if (event.animationName === RESIZE_ANIMATION_NAME) {
            resetTrigger(element);
          }
        });
      }
    }
    element.__resizeListeners__.push(fn);
  }
};

/* istanbul ignore next */
var removeResizeListener = function removeResizeListener(element, fn) {
  if (!element || !element.__resizeListeners__) return;
  if (attachEvent) {
    element.detachEvent('onresize', fn);
  } else {
    element.__resizeListeners__.splice(element.__resizeListeners__.indexOf(fn), 1);
    if (!element.__resizeListeners__.length) {
      element.removeEventListener('scroll', scrollListener);
      element.__resizeTrigger__ = !element.removeChild(element.__resizeTrigger__);
    }
  }
};

/***/ }),
/* 13 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (immutable) */ __webpack_exports__["a"] = scrollIntoView;
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_vue__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_vue__);


function scrollIntoView(container, selected) {
  if (__WEBPACK_IMPORTED_MODULE_0_vue___default.a.prototype.$isServer) return;

  if (!selected) {
    container.scrollTop = 0;
    return;
  }

  var top = selected.offsetTop;
  var bottom = selected.offsetTop + selected.offsetHeight;
  var viewRectTop = container.scrollTop;
  var viewRectBottom = viewRectTop + container.clientHeight;

  if (top < viewRectTop) {
    container.scrollTop = top;
  } else if (bottom > viewRectBottom) {
    container.scrollTop = bottom - container.clientHeight;
  }
}

/***/ }),
/* 14 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;return _c('div',{directives:[{name:"clickoutside",rawName:"v-clickoutside",value:(_vm.handleClose),expression:"handleClose"}],staticClass:"el-select selectTable",class:[_vm.selectSize ? 'el-select--' + _vm.selectSize : '']},[(_vm.multiple)?_c('div',{ref:"tags",staticClass:"el-select__tags",style:({ 'max-width': _vm.inputWidth - 32 + 'px' }),on:{"click":function($event){$event.stopPropagation();_vm.toggleMenu($event)}}},[(_vm.collapseTags && _vm.selected.length)?_c('span',[_c('el-tag',{attrs:{"closable":!_vm.disabled,"size":_vm.collapseTagSize,"hit":_vm.selected[0].hitState,"type":"info","disable-transitions":""},on:{"close":function($event){_vm.deleteTag($event, _vm.selected[0])}}},[_c('span',{staticClass:"el-select__tags-text"},[_vm._v(_vm._s(_vm.selected[0][_vm.labelKey]))])]),_vm._v(" "),(_vm.selected.length > 1)?_c('el-tag',{attrs:{"closable":false,"size":_vm.collapseTagSize,"type":"info","disable-transitions":""}},[_c('span',{staticClass:"el-select__tags-text"},[_vm._v("+ "+_vm._s(_vm.selected.length - 1))])]):_vm._e()],1):_vm._e(),_vm._v(" "),(!_vm.collapseTags)?_c('transition-group',{on:{"after-leave":_vm.resetInputHeight}},_vm._l((_vm.selected),function(item){return _c('el-tag',{key:_vm.getValueKey(item),attrs:{"closable":!_vm.disabled,"size":"small","hit":item.hitState,"type":"info","disable-transitions":""},on:{"close":function($event){_vm.deleteTag($event, item)}}},[_c('span',{staticClass:"el-select__tags-text"},[_vm._v(_vm._s(item[_vm.labelKey]))])])})):_vm._e(),_vm._v(" "),(_vm.filterable)?_c('input',{directives:[{name:"model",rawName:"v-model",value:(_vm.query),expression:"query"}],ref:"input",staticClass:"el-select__input",class:[_vm.selectSize ? ("is-" + _vm.selectSize) : ''],style:({ width: _vm.inputLength + 'px', 'max-width': _vm.inputWidth - 42 + 'px' }),attrs:{"type":"text","disabled":_vm.disabled,"debounce":_vm.remote ? 300 : 0},domProps:{"value":(_vm.query)},on:{"focus":_vm.handleFocus,"click":function($event){$event.stopPropagation();},"keyup":_vm.managePlaceholder,"keydown":[_vm.resetInputState,function($event){if(!('button' in $event)&&_vm._k($event.keyCode,"down",40,$event.key)){ return null; }$event.preventDefault();_vm.navigateRows('next')},function($event){if(!('button' in $event)&&_vm._k($event.keyCode,"up",38,$event.key)){ return null; }$event.preventDefault();_vm.navigateRows('prev')},function($event){if(!('button' in $event)&&_vm._k($event.keyCode,"enter",13,$event.key)){ return null; }$event.preventDefault();_vm.selectRow($event)},function($event){if(!('button' in $event)&&_vm._k($event.keyCode,"esc",27,$event.key)){ return null; }$event.stopPropagation();$event.preventDefault();_vm.visible = false},function($event){if(!('button' in $event)&&_vm._k($event.keyCode,"delete",[8,46],$event.key)){ return null; }_vm.deletePrevTag($event)}],"input":[function($event){if($event.target.composing){ return; }_vm.query=$event.target.value},function (e) { return _vm.handleQueryChange(e.target.value); }]}}):_vm._e()],1):_vm._e(),_vm._v(" "),_c('el-input',{ref:"reference",class:{ 'is-focus': _vm.visible },attrs:{"type":"text","placeholder":_vm.currentPlaceholder,"name":_vm.name,"id":_vm.id,"size":_vm.selectSize,"disabled":_vm.disabled,"readonly":!_vm.filterable || _vm.multiple,"validate-event":false},on:{"focus":_vm.handleFocus,"blur":_vm.handleBlur},nativeOn:{"mousedown":function($event){_vm.handleMouseDown($event)},"keyup":function($event){_vm.debouncedOnInputChange($event)},"keydown":[function($event){if(!('button' in $event)&&_vm._k($event.keyCode,"down",40,$event.key)){ return null; }$event.stopPropagation();$event.preventDefault();_vm.navigateRows('next')},function($event){if(!('button' in $event)&&_vm._k($event.keyCode,"up",38,$event.key)){ return null; }$event.stopPropagation();$event.preventDefault();_vm.navigateRows('prev')},function($event){if(!('button' in $event)&&_vm._k($event.keyCode,"enter",13,$event.key)){ return null; }$event.preventDefault();_vm.selectRow($event)},function($event){if(!('button' in $event)&&_vm._k($event.keyCode,"esc",27,$event.key)){ return null; }$event.stopPropagation();$event.preventDefault();_vm.visible = false},function($event){if(!('button' in $event)&&_vm._k($event.keyCode,"tab",9,$event.key)){ return null; }_vm.visible = false}],"paste":function($event){_vm.debouncedOnInputChange($event)},"mouseenter":function($event){_vm.inputHovering = true},"mouseleave":function($event){_vm.inputHovering = false}},model:{value:(_vm.selectedLabel),callback:function ($$v) {_vm.selectedLabel=$$v},expression:"selectedLabel"}},[_c('i',{class:['el-select__caret', 'el-input__icon', 'el-icon-' + _vm.iconClass],attrs:{"slot":"suffix"},on:{"click":_vm.handleIconClick},slot:"suffix"})]),_vm._v(" "),_c('transition',{attrs:{"name":"el-zoom-in-top"},on:{"before-enter":_vm.handleMenuEnter,"after-leave":_vm.doDestroy}},[_c('div',{directives:[{name:"show",rawName:"v-show",value:(_vm.visible),expression:"visible"}],staticClass:"fd-select-dropdown"},[_c('el-scrollbar',{directives:[{name:"show",rawName:"v-show",value:(_vm.treeData.length > 0 && !_vm.loading),expression:"treeData.length > 0 && !loading"}],ref:"scrollbar",class:{ 'is-empty': !_vm.allowCreate && _vm.query && _vm.filteredOptionsCount === 0 },attrs:{"tag":"ul","wrap-class":"el-select-dropdown__wrap","view-class":"el-select-dropdown__list"}},[_c('el-tree',{ref:"tree2",staticClass:"filter-tree",attrs:{"data":_vm.treeData,"show-checkbox":_vm.multiple,"props":_vm.defaultProps,"default-expand-all":"","filter-node-method":_vm.filterNode,"node-key":"id","highlight-current":""},on:{"node-click":_vm.nodeClick,"check-change":_vm.checkChange}})],1)],1)])],1)}
var staticRenderFns = []
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);

/***/ })
/******/ ]);
});
//# sourceMappingURL=selecttree.js.map