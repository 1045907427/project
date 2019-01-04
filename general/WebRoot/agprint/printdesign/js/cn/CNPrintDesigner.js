(function webpackUniversalModuleDefinition(root, factory) {
	if(typeof exports === 'object' && typeof module === 'object')
		module.exports = factory();
	else if(typeof define === 'function' && define.amd)
		define([], factory);
	else if(typeof exports === 'object')
		exports["CNPrintDesigner"] = factory();
	else
		root["CNPrintDesigner"] = factory();
})(this, function() {
return /******/ (function(modules) { // webpackBootstrap
/******/ 	// install a JSONP callback for chunk loading
/******/ 	var parentJsonpFunction = window["webpackJsonpCNPrintDesigner"];
/******/ 	window["webpackJsonpCNPrintDesigner"] = function webpackJsonpCallback(chunkIds, moreModules) {
/******/ 		// add "moreModules" to the modules object,
/******/ 		// then flag all "chunkIds" as loaded and fire callback
/******/ 		var moduleId, chunkId, i = 0, callbacks = [];
/******/ 		for(;i < chunkIds.length; i++) {
/******/ 			chunkId = chunkIds[i];
/******/ 			if(installedChunks[chunkId])
/******/ 				callbacks.push.apply(callbacks, installedChunks[chunkId]);
/******/ 			installedChunks[chunkId] = 0;
/******/ 		}
/******/ 		for(moduleId in moreModules) {
/******/ 			if(Object.prototype.hasOwnProperty.call(moreModules, moduleId)) {
/******/ 				modules[moduleId] = moreModules[moduleId];
/******/ 			}
/******/ 		}
/******/ 		if(parentJsonpFunction) parentJsonpFunction(chunkIds, moreModules);
/******/ 		while(callbacks.length)
/******/ 			callbacks.shift().call(null, __webpack_require__);
/******/ 		if(moreModules[0]) {
/******/ 			installedModules[0] = 0;
/******/ 			return __webpack_require__(0);
/******/ 		}
/******/ 	};
/******/
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// object to store loaded and loading chunks
/******/ 	// "0" means "already loaded"
/******/ 	// Array means "loading", array contains callbacks
/******/ 	var installedChunks = {
/******/ 		0:0
/******/ 	};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;
/******/
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/ 	// This file contains only the entry chunk.
/******/ 	// The chunk loading function for additional chunks
/******/ 	__webpack_require__.e = function requireEnsure(chunkId, callback) {
/******/ 		// "0" is the signal for "already loaded"
/******/ 		if(installedChunks[chunkId] === 0)
/******/ 			return callback.call(null, __webpack_require__);
/******/
/******/ 		// an array means "currently loading".
/******/ 		if(installedChunks[chunkId] !== undefined) {
/******/ 			installedChunks[chunkId].push(callback);
/******/ 		} else {
/******/ 			// start chunk loading
/******/ 			installedChunks[chunkId] = [callback];
/******/ 			var head = document.getElementsByTagName('head')[0];
/******/ 			var script = document.createElement('script');
/******/ 			script.type = 'text/javascript';
/******/ 			script.charset = 'utf-8';
/******/ 			script.async = true;
/******/
/******/ 			script.src = __webpack_require__.p + "" + chunkId + "." + ({"1":"bundle"}[chunkId]||chunkId) + ".js";
/******/ 			head.appendChild(script);
/******/ 		}
/******/ 	};
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "/build/";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(2);


/***/ }),
/* 1 */,
/* 2 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	exports.CommandFactory = CommandFactory_1.default;
	var Dispatcher_1 = __webpack_require__(4);
	exports.Dispatcher = Dispatcher_1.Dispatcher;
	var EventManager_1 = __webpack_require__(5);
	exports.EventManager = EventManager_1.EventManager;
	exports.Subscribe = EventManager_1.Subscribe;
	var Command_1 = __webpack_require__(6);
	var Enum_1 = __webpack_require__(92);
	exports.ResourceType = Enum_1.ResourceType;
	__webpack_require__(7);
	/**
	 * 扩展命令
	 */
	function extendCommand(SubCommand, object) {
	    SubCommand.prototype = Object.create(Command_1.Command.prototype);
	    Object.getOwnPropertyNames(object).forEach(function (name) {
	        SubCommand.prototype[name] = object[name];
	    });
	    return SubCommand;
	}
	exports.extendCommand = extendCommand;
	/**
	 * 注册命令
	 */
	function registerCommand(key, createtor) {
	    CommandFactory_1.default.Instance.register(key, createtor);
	}
	exports.registerCommand = registerCommand;


/***/ }),
/* 3 */
/***/ (function(module, exports) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	/**
	 * 命令工厂
	 */
	var CommandFactory = (function () {
	    function CommandFactory() {
	        this._commands = {};
	    }
	    Object.defineProperty(CommandFactory, "Instance", {
	        /**
	         * 单例对象
	         */
	        get: function () {
	            if (CommandFactory._instance) {
	                return CommandFactory._instance;
	            }
	            CommandFactory._instance = new CommandFactory();
	            return CommandFactory._instance;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    /**
	     * 注册命令
	     */
	    CommandFactory.prototype.register = function (name, factory) {
	        this._commands[name] = factory;
	    };
	    /**
	     * 根据action返回命令对象
	     */
	    CommandFactory.prototype.getCommand = function (action) {
	        action.context = CommandFactory.Instance.Context;
	        return this._commands[action.type](action);
	    };
	    return CommandFactory;
	}());
	exports.CommandFactory = CommandFactory;
	exports.default = CommandFactory;


/***/ }),
/* 4 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	exports.CommandFactory = CommandFactory_1.default;
	var EventManager_1 = __webpack_require__(5);
	exports.EventManager = EventManager_1.EventManager;
	exports.Subscribe = EventManager_1.Subscribe;
	var Command_1 = __webpack_require__(6);
	__webpack_require__(7);
	/**
	 * 撤销命令
	 */
	var UndoCommand = (function (_super) {
	    __extends(UndoCommand, _super);
	    function UndoCommand() {
	        return _super !== null && _super.apply(this, arguments) || this;
	    }
	    UndoCommand.prototype.canUndo = function () {
	        return false;
	    };
	    UndoCommand.prototype.execute = function () {
	        var command = Dispatcher.undoCommandStack.pop();
	        while (!command.canUndo()) {
	            command = Dispatcher.undoCommandStack.pop();
	        }
	        if (command) {
	            var snapshot = command.createSnapshot();
	            Dispatcher.redoCommandStack.push(command);
	            command.undo();
	            command.snapshot = snapshot;
	        }
	    };
	    return UndoCommand;
	}(Command_1.Command));
	CommandFactory_1.default.Instance.register('UNDO_COMMAND', function (action) {
	    return new UndoCommand(action.context);
	});
	/**
	 * 恢复命令
	 */
	var RedoCommand = (function (_super) {
	    __extends(RedoCommand, _super);
	    function RedoCommand() {
	        return _super !== null && _super.apply(this, arguments) || this;
	    }
	    RedoCommand.prototype.canUndo = function () {
	        return false;
	    };
	    RedoCommand.prototype.execute = function () {
	        var command = Dispatcher.redoCommandStack.pop();
	        if (command) {
	            var snapshot = command.createSnapshot();
	            Dispatcher.undoCommandStack.push(command);
	            command.execute();
	            command.snapshot = snapshot;
	        }
	    };
	    return RedoCommand;
	}(Command_1.Command));
	CommandFactory_1.default.Instance.register('REDO_COMMAND', function (action) {
	    return new RedoCommand(action.context);
	});
	/**
	 * 分发器：分发命令和事件到注册的对象上；
	 */
	var Dispatcher = (function () {
	    function Dispatcher() {
	    }
	    Dispatcher.dispatch = function (action) {
	        if (action.event) {
	            EventManager_1.EventManager.broadcast(action.type, action);
	            return;
	        }
	        Dispatcher.executeCommand(action);
	    };
	    Dispatcher.executeCommand = function (action) {
	        var command = CommandFactory_1.default.Instance.getCommand(action);
	        if (!command) {
	            return;
	        }
	        if (!command.canUndo()) {
	            command.execute();
	            return;
	        }
	        var snapshot = command.createSnapshot();
	        Dispatcher.undoCommandStack.push(command);
	        command.execute();
	        command.snapshot = snapshot;
	    };
	    return Dispatcher;
	}());
	Dispatcher.undoCommandStack = [];
	Dispatcher.redoCommandStack = [];
	exports.Dispatcher = Dispatcher;


/***/ }),
/* 5 */
/***/ (function(module, exports) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	/**
	 * 事件管理器，单例
	 */
	var EventManager = (function () {
	    function EventManager() {
	    }
	    Object.defineProperty(EventManager, "Instance", {
	        get: function () {
	            if (EventManager._instance) {
	                return EventManager._instance;
	            }
	            EventManager._instance = new EventManager();
	            return EventManager._instance;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    EventManager.prototype.register = function (name, handler) {
	        if (!EventManager.listener[name]) {
	            EventManager.listener[name] = [];
	        }
	        EventManager.listener[name].push(handler);
	    };
	    EventManager.prototype.unregister = function (name, handler) {
	        var listenerList = EventManager.listener[name];
	        if (listenerList) {
	            EventManager.listener[name] = listenerList.filter(function (item) { return item != handler; });
	        }
	    };
	    EventManager.prototype.broadcast = function (name, data) {
	        var listeners = EventManager.listener[name];
	        if (listeners) {
	            for (var _i = 0, listeners_1 = listeners; _i < listeners_1.length; _i++) {
	                var handler = listeners_1[_i];
	                handler(data);
	            }
	        }
	    };
	    return EventManager;
	}());
	EventManager.listener = {};
	var EventManagerInstance = EventManager.Instance;
	exports.EventManager = EventManagerInstance;
	function Subscribe(eventType) {
	    var events = eventType.split(',');
	    return function (target, key, descriptor) {
	        var componentWillMount = target.componentWillMount;
	        var componentWillUnmount = target.componentWillUnmount;
	        Object.defineProperty(target, 'componentWillMount', {
	            configurable: true,
	            value: function () {
	                var handler = this[key];
	                events.forEach(function (eventName) {
	                    EventManagerInstance.register(eventName, handler);
	                });
	                if (componentWillMount) {
	                    componentWillMount.call(this);
	                }
	            }
	        });
	        Object.defineProperty(target, 'componentWillUnmount', {
	            configurable: true,
	            value: function () {
	                var handler = this[key];
	                events.forEach(function (eventName) {
	                    EventManagerInstance.unregister(eventName, handler);
	                });
	                if (componentWillUnmount) {
	                    componentWillUnmount.call(this);
	                }
	            }
	        });
	    };
	}
	exports.Subscribe = Subscribe;
	exports.default = EventManagerInstance;


/***/ }),
/* 6 */
/***/ (function(module, exports) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	/**
	 * 抽象命令
	 */
	var Command = (function () {
	    function Command(context) {
	        this.context = context;
	    }
	    Object.defineProperty(Command.prototype, "snapshot", {
	        get: function () {
	            return this._snapshot;
	        },
	        set: function (value) {
	            this._snapshot = value;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    ;
	    Command.prototype.createSnapshot = function () {
	        return null;
	    };
	    Command.prototype.undo = function () {
	    };
	    Command.prototype.execute = function () {
	    };
	    Command.prototype.canExecute = function () {
	        return true;
	    };
	    Command.prototype.canUndo = function () {
	        return false;
	    };
	    return Command;
	}());
	exports.Command = Command;
	exports.default = Command;


/***/ }),
/* 7 */
/***/ (function(module, exports, __webpack_require__) {

	var config = __webpack_require__(8);
	function loadCommand() {
	    var commands = config.commands;
	    for (var _i = 0, commands_1 = commands; _i < commands_1.length; _i++) {
	        var path = commands_1[_i];
	        __webpack_require__(9)("./" + path + ".ts");
	    }
	}
	loadCommand();


/***/ }),
/* 8 */
/***/ (function(module, exports) {

	module.exports = {"commands":["InitCommand","InsertTextCommand","LoadCommand","DeleteCommand","InsertImageCommand","InsertQrcodeCommand","InsertBarcodeCommand","PropertyChangeCommand","DragCommand","InsertLineCommand","InsertRectCommand","InsertTableCommand","ZoomCommand","PasteCommand","CopyCommand","ZIndexCommand","SaveCommand","PreviewCommand","PublishCommand","ToggleEditModeCommand","InsertComponentCommand","LoadComponentCommand","LoadComponentListCommand","LoadSideListCommand","InsertTableColumnCommand","DeleteTableColumnCommand","InsertTableRowCommand","DeleteTableRowCommand","ModifyTextScriptCommand","UpdateResouceCommand","ResourcePublishCommand"]}

/***/ }),
/* 9 */
/***/ (function(module, exports, __webpack_require__) {

	var map = {
		"./CopyCommand.ts": 10,
		"./DeleteCommand.ts": 12,
		"./DeleteTableColumnCommand.ts": 13,
		"./DeleteTableRowCommand.ts": 14,
		"./DragCommand.ts": 15,
		"./InitCommand.ts": 20,
		"./InsertBarcodeCommand.ts": 139,
		"./InsertComponentCommand.ts": 140,
		"./InsertImageCommand.ts": 141,
		"./InsertLineCommand.ts": 142,
		"./InsertQrcodeCommand.ts": 143,
		"./InsertRectCommand.ts": 144,
		"./InsertTableColumnCommand.ts": 145,
		"./InsertTableCommand.ts": 146,
		"./InsertTableRowCommand.ts": 147,
		"./InsertTextCommand.ts": 148,
		"./LoadCommand.ts": 149,
		"./LoadComponentCommand.ts": 150,
		"./LoadComponentListCommand.ts": 151,
		"./LoadSideListCommand.ts": 152,
		"./LoadTemplateListCommand.ts": 153,
		"./Loader.ts": 7,
		"./ModifyTextScriptCommand.ts": 154,
		"./PasteCommand.ts": 155,
		"./PreviewCommand.ts": 156,
		"./PropertyChangeCommand.ts": 158,
		"./PublishCommand.ts": 159,
		"./ResourcePublishCommand.ts": 160,
		"./SaveCommand.ts": 161,
		"./ToggleEditModeCommand.ts": 162,
		"./UpdateResouceCommand.ts": 163,
		"./ZIndexCommand.ts": 164,
		"./ZoomCommand.ts": 165
	};
	function webpackContext(req) {
		return __webpack_require__(webpackContextResolve(req));
	};
	function webpackContextResolve(req) {
		return map[req] || (function() { throw new Error("Cannot find module '" + req + "'.") }());
	};
	webpackContext.keys = function webpackContextKeys() {
		return Object.keys(map);
	};
	webpackContext.resolve = webpackContextResolve;
	module.exports = webpackContext;
	webpackContext.id = 9;


/***/ }),
/* 10 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var Clipboard_1 = __webpack_require__(11);
	/**
	 * 删除元素命令
	 */
	var CopyCommand = (function (_super) {
	    __extends(CopyCommand, _super);
	    function CopyCommand(context) {
	        var _this = _super.call(this, context) || this;
	        _this.clipboard = new Clipboard_1.Clipboard(context);
	        return _this;
	    }
	    CopyCommand.prototype.execute = function () {
	        console.log('CopyCommand');
	        this.clipboard.copy();
	    };
	    return CopyCommand;
	}(Command_1.default));
	//注册命令到系统中
	CommandFactory_1.default.Instance.register('COPY_COMMAND', function (action) {
	    return new CopyCommand(action.context);
	});
	//导出模块
	exports.default = CopyCommand;


/***/ }),
/* 11 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	var Dispatcher_1 = __webpack_require__(4);
	var offset = 10;
	var Clipboard = (function () {
	    function Clipboard(designerContext) {
	        this.designerContext = designerContext;
	    }
	    Clipboard.prototype.copy = function () {
	        var selected = this.getSelectedElements();
	        Clipboard.content = selected.map(function (element) {
	            // var cloned = element.clone();
	            // var ret = cloned;
	            if (element.parent && element.parent.forElement == element) {
	                // ret = element.parent.clone();
	                return element.parent;
	            }
	            return element;
	        });
	        Dispatcher_1.EventManager.broadcast('DESINGER_CONTEXTMENU_COPY_EVENT', { event: { target: this } });
	    };
	    Clipboard.prototype.getSelectedElements = function () {
	        return this.designerContext.editorPanel.actived;
	    };
	    Clipboard.prototype.paste = function () {
	        var _this = this;
	        var isClipboardEmpty = Clipboard.content.length < 1;
	        var elements = [];
	        if (isClipboardEmpty) {
	            return elements;
	        }
	        Clipboard.content.forEach((function (element) {
	            var cloned = element.clone();
	            var ret = cloned;
	            if (element.parent && element.parent.forElement == element) {
	                ret = element.parent.clone();
	            }
	            elements.push(ret);
	            _this.designerContext.editorPanel.add(ret);
	        }).bind(this));
	        return elements;
	    };
	    return Clipboard;
	}());
	Clipboard.content = [];
	exports.Clipboard = Clipboard;


/***/ }),
/* 12 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	/**
	 * 删除元素命令
	 */
	var DeleteCommand = (function (_super) {
	    __extends(DeleteCommand, _super);
	    function DeleteCommand(context) {
	        var _this = _super.call(this, context) || this;
	        //继承Command扩展功能
	        _this.elments = [];
	        return _this;
	    }
	    DeleteCommand.prototype.canUndo = function () {
	        return !this.context.editorPanel.editing;
	    };
	    DeleteCommand.prototype.execute = function () {
	        //这里实现删除业务逻辑
	        var _this = this;
	        if (this.context.editorPanel.editing) {
	            return;
	        }
	        this.context.editorPanel.actived.forEach(function (item) {
	            var parent = item.parent;
	            if (parent.forElement && parent.forElement.id == item.id) {
	                parent.parent.removeChild(parent);
	                item = parent;
	            }
	            _this.elments.push(item);
	            _this.context.editorPanel.remove(item);
	        });
	    };
	    DeleteCommand.prototype.undo = function () {
	        var _this = this;
	        this.elments.forEach(function (item) {
	            _this.context.editorPanel.add(item);
	        });
	    };
	    return DeleteCommand;
	}(Command_1.default));
	//注册命令到系统中
	CommandFactory_1.default.Instance.register('DELETE_COMMAND', function (action) {
	    return new DeleteCommand(action.context);
	});
	//导出模块
	exports.default = DeleteCommand;


/***/ }),
/* 13 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var Index_1 = __webpack_require__(2);
	/**
	 * 删除列
	 */
	var DeleteTableColumnCommand = (function (_super) {
	    __extends(DeleteTableColumnCommand, _super);
	    function DeleteTableColumnCommand(context) {
	        var _this = _super.call(this, context) || this;
	        _this.elements = [];
	        return _this;
	    }
	    DeleteTableColumnCommand.prototype.canUndo = function () {
	        return false;
	    };
	    DeleteTableColumnCommand.prototype.execute = function () {
	        var contextCell = this.context.editorPanel.actived[0];
	        var index = contextCell.index[1];
	        var table = contextCell.row.table;
	        table.removeColumn(index);
	        this.context.editorPanel.blur();
	        Index_1.EventManager.broadcast('DESINGNER_BLUR_EVENT', { event: { target: this } });
	    };
	    DeleteTableColumnCommand.prototype.undo = function () {
	    };
	    return DeleteTableColumnCommand;
	}(Command_1.default));
	CommandFactory_1.default.Instance.register('DELETE_TABLE_COLUMN_COMMAND', function (action) {
	    var cmd = new DeleteTableColumnCommand(action.context);
	    return cmd;
	});
	exports.default = DeleteTableColumnCommand;


/***/ }),
/* 14 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var Index_1 = __webpack_require__(2);
	/**
	 * 删除列
	 */
	var DeleteTableRowCommand = (function (_super) {
	    __extends(DeleteTableRowCommand, _super);
	    function DeleteTableRowCommand(context) {
	        var _this = _super.call(this, context) || this;
	        _this.elements = [];
	        return _this;
	    }
	    DeleteTableRowCommand.prototype.canUndo = function () {
	        return false;
	    };
	    DeleteTableRowCommand.prototype.execute = function () {
	        var contextCell = this.context.editorPanel.actived[0];
	        var contextRow = contextCell.row;
	        var index = contextRow.index;
	        var table = contextCell.row.table;
	        table.removeRow(index);
	        this.context.editorPanel.blur();
	        Index_1.EventManager.broadcast('DESINGNER_BLUR_EVENT', { event: { target: this } });
	    };
	    DeleteTableRowCommand.prototype.undo = function () {
	    };
	    return DeleteTableRowCommand;
	}(Command_1.default));
	CommandFactory_1.default.Instance.register('DELETE_TABLE_ROW_COMMAND', function (action) {
	    var cmd = new DeleteTableRowCommand(action.context);
	    return cmd;
	});
	exports.default = DeleteTableRowCommand;


/***/ }),
/* 15 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var EventManager_1 = __webpack_require__(5);
	var Index_1 = __webpack_require__(16);
	/***
	 * 扩展一个支持可撤销／恢复的命令
	 * 1 提供一个命令快照类，实现接口 ISnapshot。用来保存可恢复的状态信息
	 * 2 重写(override) Command类的createSnapshot(): ISnapshot方法，创建快照对象
	 * 3 重写(override) Command类的 undo():void方法，实现自己的撤销逻辑
	 */
	var DragSnapshot = (function () {
	    function DragSnapshot(state) {
	        this.state = state;
	    }
	    return DragSnapshot;
	}());
	/**
	 * 拖动命令
	 */
	var DragCommand = (function (_super) {
	    __extends(DragCommand, _super);
	    function DragCommand(context) {
	        var _this = _super.call(this, context) || this;
	        //继承Command扩展功能
	        _this.origin = { left: 0, top: 0 };
	        _this.end = { left: 0, top: 0 };
	        _this.elements = [];
	        _this.onDragStart = _this.onDragStart.bind(_this);
	        _this.onDrag = _this.onDrag.bind(_this);
	        _this.onDragEnd = _this.onDragEnd.bind(_this);
	        EventManager_1.default.register('DESINGER_DRAGSTART_EVENT', _this.onDragStart);
	        EventManager_1.default.register('DESINGER_DRAG_EVENT', _this.onDrag);
	        EventManager_1.default.register('DESINGER_DRAGEND_EVENT', _this.onDragEnd);
	        return _this;
	    }
	    DragCommand.prototype.createSnapshot = function () {
	        var state = {};
	        this.elements.forEach((function (item) {
	            state[item.id] = {
	                left: Number(item.left),
	                top: Number(item.top)
	            };
	        }).bind(this));
	        return new DragSnapshot(state);
	    };
	    DragCommand.prototype.canUndo = function () {
	        if (!this.snapshot) {
	            return true;
	        }
	        return this.end.left != this.origin.left || this.end.top != this.origin.top;
	        // var snapshot = <DragSnapshot>this.snapshot;
	        // return this.elements.some(item=>snapshot.state[item.id].left != this.origin.left || snapshot.state[item.id].top != this.origin.top)
	    };
	    DragCommand.prototype.onDragStart = function (args) {
	        this.origin.left = args.clientX;
	        this.origin.top = args.clientY;
	        this.snapshot = this.createSnapshot();
	    };
	    DragCommand.prototype.onDrag = function (args) {
	        var dx = Index_1.Unit.toMillimeter(args.clientX - this.origin.left);
	        var dy = Index_1.Unit.toMillimeter(args.clientY - this.origin.top);
	        var snapshot = this.snapshot;
	        var dragState = snapshot.state;
	        this.elements.forEach((function (item) {
	            var left = Number(dragState[item.id].left + dx).toFixed(2);
	            var top = Number(dragState[item.id].top + dy).toFixed(2);
	            item.update([
	                {
	                    propertyName: 'left',
	                    propertyValue: left
	                },
	                {
	                    propertyName: 'top',
	                    propertyValue: top
	                }
	            ]);
	        }).bind(this));
	    };
	    DragCommand.prototype.onDragEnd = function (args) {
	        this.end.left = args.clientX;
	        this.end.top = args.clientY;
	        console.log('onDragEnd onDragEnd onDragEnd onDragEnd');
	        EventManager_1.default.unregister('DESINGER_DRAGSTART_EVENT', this.onDragStart);
	        EventManager_1.default.unregister('DESINGER_DRAG_EVENT', this.onDrag);
	        EventManager_1.default.unregister('DESINGER_DRAGEND_EVENT', this.onDragEnd);
	    };
	    DragCommand.prototype.execute = function () {
	        if (!this.snapshot) {
	            return;
	        }
	        var snapshot = this.snapshot;
	        var dragSate = snapshot.state;
	        this.elements.forEach((function (item) {
	            var left = dragSate[item.id].left;
	            var top = dragSate[item.id].top;
	            item.update([
	                {
	                    propertyName: 'left',
	                    propertyValue: left
	                },
	                {
	                    propertyName: 'top',
	                    propertyValue: top
	                },
	            ]);
	        }).bind(this));
	    };
	    DragCommand.prototype.undo = function () {
	        var snapshot = this.snapshot;
	        var dragState = snapshot.state;
	        this.elements.forEach((function (item) {
	            var left = dragState[item.id].left;
	            var top = dragState[item.id].top;
	            item.update([
	                {
	                    propertyName: 'left',
	                    propertyValue: left
	                },
	                {
	                    propertyName: 'top',
	                    propertyValue: top
	                },
	            ]);
	        }).bind(this));
	    };
	    return DragCommand;
	}(Command_1.Command));
	/**
	 * 拖动位置
	 */
	var MoveDragCommand = (function (_super) {
	    __extends(MoveDragCommand, _super);
	    function MoveDragCommand() {
	        return _super !== null && _super.apply(this, arguments) || this;
	    }
	    return MoveDragCommand;
	}(DragCommand));
	/**
	 * 拖动大小
	 */
	var ResizeDragCommand = (function (_super) {
	    __extends(ResizeDragCommand, _super);
	    function ResizeDragCommand() {
	        return _super !== null && _super.apply(this, arguments) || this;
	    }
	    ResizeDragCommand.prototype.createSnapshot = function () {
	        var state = {};
	        this.elements.forEach((function (item) {
	            state[item.id] = {
	                width: Number(item.width),
	                height: Number(item.height),
	                left: Number(item.left),
	                top: Number(item.top)
	            };
	        }).bind(this));
	        return new DragSnapshot(state);
	    };
	    ResizeDragCommand.prototype.update = function (item, dragState, dx, dy) {
	        if (this.pointer == 'line-lm') {
	            var startX = Number(dragState[item.id].left + dx).toFixed(2);
	            item.update([
	                {
	                    propertyName: 'startX',
	                    propertyValue: startX
	                }
	            ]);
	            return;
	        }
	        if (this.pointer == 'line-rm') {
	            var endX = Number(dragState[item.id].width + dx + dragState[item.id].left).toFixed(2);
	            item.update([
	                {
	                    propertyName: 'endX',
	                    propertyValue: endX
	                }
	            ]);
	            return;
	        }
	        if (this.pointer == 'line-mb') {
	            var endY = Number(dragState[item.id].height + dy + dragState[item.id].top).toFixed(2);
	            item.update([
	                {
	                    propertyName: 'endY',
	                    propertyValue: Number(endY)
	                }
	            ]);
	            return;
	        }
	        if (this.pointer == 'line-mt') {
	            var startY = Number(dy + dragState[item.id].top).toFixed(2);
	            item.update([
	                {
	                    propertyName: 'startY',
	                    propertyValue: Number(startY)
	                }
	            ]);
	            return;
	        }
	        if (this.pointer == "cell-lm") {
	            var width = Number(dragState[item.id].width - dx).toFixed(2);
	            var left = Number(dragState[item.id].left + dx).toFixed(2);
	            item.column = {
	                width: width,
	                control: "cell-lm",
	                left: left
	            };
	            return;
	        }
	        if (this.pointer == "cell-rm") {
	            var width = Number(dragState[item.id].width + dx).toFixed(2);
	            item.column = {
	                width: width,
	                control: "cell-rm"
	            };
	            return;
	        }
	        if (this.pointer == 'cell-mb') {
	            var height = Number(dragState[item.id].height + dy).toFixed(2);
	            item.row.resize({
	                height: height,
	                pointer: "cell-mb"
	            });
	            return;
	        }
	        if (this.pointer == 'cell-mt') {
	            var height = Number(dragState[item.id].height - dy).toFixed(2);
	            var top_1 = Number(dragState[item.id].top + dy).toFixed(2);
	            item.row.resize({
	                height: height,
	                top: top_1,
	                pointer: "cell-mt"
	            });
	            return;
	        }
	        if (this.pointer == 'lm') {
	            var width = Number(dragState[item.id].width - dx).toFixed(2);
	            var left = Number(dragState[item.id].left + dx).toFixed(2);
	            console.log("left:" + left);
	            item.update([
	                {
	                    propertyName: 'left',
	                    propertyValue: left
	                },
	                {
	                    propertyName: 'width',
	                    propertyValue: width
	                }
	            ]);
	            return;
	        }
	        if (this.pointer == 'rm') {
	            var width = Number(dragState[item.id].width + dx).toFixed(2);
	            item.update([
	                {
	                    propertyName: 'width',
	                    propertyValue: width
	                }
	            ]);
	            return;
	        }
	        if (this.pointer == 'mt') {
	            var height = Number(dragState[item.id].height - dy).toFixed(2);
	            var top_2 = Number(dragState[item.id].top + dy).toFixed(2);
	            item.update([
	                {
	                    propertyName: 'top',
	                    propertyValue: top_2
	                },
	                {
	                    propertyName: 'height',
	                    propertyValue: height
	                }
	            ]);
	            return;
	        }
	        if (this.pointer == 'mb') {
	            var height = Number(dragState[item.id].height + dy).toFixed(2);
	            item.update([
	                {
	                    propertyName: 'height',
	                    propertyValue: height
	                }
	            ]);
	            return;
	        }
	        if (this.pointer == 'rb') {
	            var width = Number(dragState[item.id].width + dx).toFixed(2);
	            var height = Number(dragState[item.id].height * (Number(width) / dragState[item.id].width)).toFixed(2);
	            item.update([
	                {
	                    propertyName: 'width',
	                    propertyValue: width
	                },
	                {
	                    propertyName: 'height',
	                    propertyValue: height
	                }
	            ]);
	            return;
	        }
	        if (this.pointer == 'rt') {
	            var width = Number(dragState[item.id].width + dx).toFixed(2);
	            var height = Number(dragState[item.id].height * (Number(width) / dragState[item.id].width)).toFixed(2);
	            var offsetY = dragState[item.id].height - Number(height);
	            var top_3 = Number(dragState[item.id].top + offsetY).toFixed(2);
	            item.update([
	                {
	                    propertyName: 'width',
	                    propertyValue: width
	                },
	                {
	                    propertyName: 'height',
	                    propertyValue: height
	                },
	                {
	                    propertyName: "top",
	                    propertyValue: top_3
	                }
	            ]);
	            return;
	        }
	        if (this.pointer == 'lt') {
	            var width = Number(dragState[item.id].width - dx).toFixed(2);
	            var height = Number(dragState[item.id].height * (Number(width) / dragState[item.id].width)).toFixed(2);
	            var offsetY = dragState[item.id].height - Number(height);
	            var top_4 = Number(dragState[item.id].top + offsetY).toFixed(2);
	            var left = dragState[item.id].left + dx;
	            item.update([
	                {
	                    propertyName: 'width',
	                    propertyValue: width
	                },
	                {
	                    propertyName: 'height',
	                    propertyValue: height
	                },
	                {
	                    propertyName: "top",
	                    propertyValue: top_4
	                },
	                {
	                    propertyName: "left",
	                    propertyValue: left
	                }
	            ]);
	            return;
	        }
	        if (this.pointer == 'lb') {
	            var width = Number(dragState[item.id].width - dx).toFixed(2);
	            var left = dragState[item.id].left + dx;
	            var height = Number(dragState[item.id].height * (Number(width) / dragState[item.id].width)).toFixed(2);
	            item.update([
	                {
	                    propertyName: "left",
	                    propertyValue: left
	                },
	                {
	                    propertyName: "width",
	                    propertyValue: width
	                },
	                {
	                    propertyName: "height",
	                    propertyValue: height
	                }
	            ]);
	            return;
	        }
	    };
	    ResizeDragCommand.prototype.onDrag = function (args) {
	        var _this = this;
	        var dx = Index_1.Unit.toMillimeter(args.clientX - this.origin.left);
	        var dy = Index_1.Unit.toMillimeter(args.clientY - this.origin.top);
	        var snapshot = this.snapshot;
	        var dragState = snapshot.state;
	        this.elements.forEach((function (item) {
	            _this.update(item, dragState, dx, dy);
	        }).bind(this));
	    };
	    ResizeDragCommand.prototype.execute = function () {
	        if (!this.snapshot) {
	            return;
	        }
	        var snapshot = this.snapshot;
	        var dragSate = snapshot.state;
	        this.elements.forEach((function (item) {
	            var width = dragSate[item.id].width;
	            var height = dragSate[item.id].height;
	            var left = dragSate[item.id].left;
	            var top = dragSate[item.id].top;
	            item.update([
	                {
	                    propertyName: 'width',
	                    propertyValue: width
	                },
	                {
	                    propertyName: 'height',
	                    propertyValue: height
	                },
	                {
	                    propertyName: 'left',
	                    propertyValue: left
	                },
	                {
	                    propertyName: 'top',
	                    propertyValue: top
	                }
	            ]);
	        }).bind(this));
	    };
	    ResizeDragCommand.prototype.undo = function () {
	        var snapshot = this.snapshot;
	        var dragState = snapshot.state;
	        this.elements.forEach((function (item) {
	            var width = dragState[item.id].width;
	            var height = dragState[item.id].height;
	            var left = dragState[item.id].left;
	            var top = dragState[item.id].top;
	            item.update([
	                {
	                    propertyName: 'width',
	                    propertyValue: width
	                },
	                {
	                    propertyName: 'height',
	                    propertyValue: height
	                },
	                {
	                    propertyName: 'left',
	                    propertyValue: left
	                },
	                {
	                    propertyName: 'top',
	                    propertyValue: top
	                }
	            ]);
	        }).bind(this));
	    };
	    return ResizeDragCommand;
	}(DragCommand));
	//注册拖动位置命令到系统中
	CommandFactory_1.default.Instance.register('MOVE_DRAG_COMMAND', function (action) {
	    var cmd = new DragCommand(action.context);
	    cmd.elements = action.data.elements;
	    return cmd;
	});
	//注册拖动大小命令到系统中
	CommandFactory_1.default.Instance.register('RIZE_DRAG_COMMAND', function (action) {
	    if (action.data.elements.some(function (e) { return e.width === null || e.height === null; })) {
	        return null;
	    }
	    var cmd = new ResizeDragCommand(action.context);
	    cmd.elements = action.data.elements;
	    cmd.pointer = action.data.pointer;
	    return cmd;
	});
	//导出模块
	exports.default = DragCommand;


/***/ }),
/* 16 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	var DOMUtils_1 = __webpack_require__(17);
	exports.DOMUtils = DOMUtils_1.default;
	var Unit_1 = __webpack_require__(18);
	exports.Unit = Unit_1.default;
	var CommonUtils_1 = __webpack_require__(19);
	exports.CommonUtils = CommonUtils_1.default;
	function GUID() {
	    return (new Date()).getTime() + '' + Math.floor(Math.random() * 1000);
	}
	exports.GUID = GUID;


/***/ }),
/* 17 */
/***/ (function(module, exports) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	var DOMUtils = {
	    ApplyStyle: function (element, style) {
	        for (var key in style) {
	            if (style.hasOwnProperty(key)) {
	                element.style[key] = style[key];
	            }
	        }
	    },
	    CreateElement: function (tag, attribute) {
	        var element = document.createElement(tag);
	        for (var key in attribute) {
	            if (key == 'className') {
	                element[key] = attribute[key];
	                continue;
	            }
	            if (attribute.hasOwnProperty(key)) {
	                element.style[key] = attribute[key];
	            }
	        }
	        return element;
	    }
	};
	exports.default = DOMUtils;


/***/ }),
/* 18 */
/***/ (function(module, exports) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	function formatFloat(number, format) {
	    if (format === void 0) { format = 2; }
	    return Number(number.toFixed(format));
	}
	/**
	 * 单位转换工具类,
	 */
	var Unit = {
	    scale: 1,
	    /**
	     * 像素转毫米
	     * @param px
	     * @returns {number}
	     */
	    toMillimeter: function (px) {
	        ///96dpi  25.4mm = 96px  1PX =  0.264583333mm  1mm = 3.77952px;
	        return formatFloat(Number((Number(px) * 0.264583333).toFixed(2)) / this.scale);
	    },
	    /**
	     * 毫米转像素
	     * @param mm
	     * @returns {number}
	     */
	    toPixel: function (mm) {
	        ///96dpi  25.4mm = 96px  1PX =  0.264583333mm  1mm = 3.77952px;
	        return formatFloat(Number((3.77952 * Number(mm)).toFixed(2)) * this.scale);
	    },
	    /**
	     * pt 转毫米
	     * @param pt
	     * @returns {number}
	     */
	    ptToMillimeter: function (pt) {
	        //磅（1/72 英寸）== (1/72 * 25.4)mm
	        return formatFloat(Number(pt) * 1 / 72 * 25.4);
	    }
	};
	exports.default = Unit;


/***/ }),
/* 19 */
/***/ (function(module, exports) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommonUtils = {
	    /**
	     * 浏览器版本检测
	     * @returns {object}
	     */
	    uaMatch: function () {
	        var s = navigator.userAgent;
	        s = s.toLowerCase();
	        var r = /(chrome)[ \/]([\w.]+)/.exec(s) ||
	            /(webkit)[ \/]([\w.]+)/.exec(s) ||
	            /(opera)(?:.*version|)[ \/]([\w.]+)/.exec(s) ||
	            /(msie) ([\w.]+)/.exec(s) ||
	            (s.indexOf("compatible") < 0) && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec(s) ||
	            [], a = {
	            browser: r[1] || "",
	            version: r[2] || "0"
	        }, b = {};
	        a.browser && (b[a.browser] = !0, b.version = a.version),
	            b.chrome ? b.webkit = !0 : b.webkit && (b.safari = !0);
	        return b;
	    },
	    /**
	     * 操作系统版本
	     */
	    uaOS: function () {
	        var sUserAgent = navigator.userAgent;
	        var isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows");
	        var isMac = (navigator.platform == "Mac68K") || (navigator.platform == "MacPPC") || (navigator.platform == "Macintosh") || (navigator.platform == "MacIntel");
	        if (isMac)
	            return "Mac";
	        var isUnix = (navigator.platform == "X11") && !isWin && !isMac;
	        if (isUnix)
	            return "Unix";
	        var isLinux = (String(navigator.platform).indexOf("Linux") > -1);
	        if (isLinux)
	            return "Linux";
	        if (isWin) {
	            var isWin2K = sUserAgent.indexOf("Windows NT 5.0") > -1 || sUserAgent.indexOf("Windows 2000") > -1;
	            if (isWin2K)
	                return "Win2000";
	            var isWinXP = sUserAgent.indexOf("Windows NT 5.1") > -1 || sUserAgent.indexOf("Windows XP") > -1;
	            if (isWinXP)
	                return "WinXP";
	            var isWin2003 = sUserAgent.indexOf("Windows NT 5.2") > -1 || sUserAgent.indexOf("Windows 2003") > -1;
	            if (isWin2003)
	                return "Win2003";
	            var isWinVista = sUserAgent.indexOf("Windows NT 6.0") > -1 || sUserAgent.indexOf("Windows Vista") > -1;
	            if (isWinVista)
	                return "WinVista";
	            var isWin7 = sUserAgent.indexOf("Windows NT 6.1") > -1 || sUserAgent.indexOf("Windows 7") > -1;
	            if (isWin7)
	                return "Win7";
	            var isWin10 = sUserAgent.indexOf("Windows NT 10") > -1 || sUserAgent.indexOf("Windows 10") > -1;
	            if (isWin10)
	                return "Win10";
	        }
	        return "other";
	    }
	};
	exports.default = CommonUtils;


/***/ }),
/* 20 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var Designer_1 = __webpack_require__(21);
	/**
	 * 设计器初始化
	 */
	var InitCommand = (function (_super) {
	    __extends(InitCommand, _super);
	    function InitCommand(context) {
	        return _super.call(this, context) || this;
	    }
	    InitCommand.prototype.execute = function () {
	        CommandFactory_1.default.Instance.Context = this.context;
	        this.context.editorPanel.render(this.data.rootDOM);
	    };
	    return InitCommand;
	}(Command_1.default));
	CommandFactory_1.default.Instance.register('INIT_COMMAND', function (action) {
	    var context = new Designer_1.default(action.data.width, action.data.height);
	    var cmd = new InitCommand(context);
	    cmd.data = action.data;
	    return cmd;
	});
	exports.default = InitCommand;


/***/ }),
/* 21 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	var EditorPanel_1 = __webpack_require__(22);
	var PropertyPanel_1 = __webpack_require__(137);
	var Enum_1 = __webpack_require__(92);
	var Server = __webpack_require__(98);
	var _ServiceConfig = __webpack_require__(138); //元素构造函数的配置信息
	Server.init({ serviceConfig: _ServiceConfig });
	/**
	 * 设计器
	 */
	var Designer = (function () {
	    function Designer(width, height) {
	        this.editMode = Enum_1.EditMode.Designer;
	        var self = this;
	        this.editorPanel = new EditorPanel_1.EditorPanel(width, height);
	        this.propertyPanel = new PropertyPanel_1.default();
	    }
	    return Designer;
	}());
	exports.default = Designer;


/***/ }),
/* 22 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	var DrawElement_1 = __webpack_require__(23);
	var Index_1 = __webpack_require__(26);
	var SelectionBox_1 = __webpack_require__(87);
	var Index_2 = __webpack_require__(16);
	var Dispatcher_1 = __webpack_require__(4);
	var Enum_1 = __webpack_require__(92);
	var TemplateParser_1 = __webpack_require__(93);
	var Server = __webpack_require__(98);
	var TableRowDrawElement_1 = __webpack_require__(31);
	var DragStateEnum;
	(function (DragStateEnum) {
	    DragStateEnum[DragStateEnum["Origin"] = -1] = "Origin";
	    DragStateEnum[DragStateEnum["Start"] = 0] = "Start";
	    DragStateEnum[DragStateEnum["Dragging"] = 1] = "Dragging";
	    DragStateEnum[DragStateEnum["End"] = 2] = "End";
	})(DragStateEnum = exports.DragStateEnum || (exports.DragStateEnum = {}));
	;
	/**
	 * 设计器编辑面版
	 */
	var EditorPanel = (function () {
	    function EditorPanel(width, height) {
	        this.actived = [];
	        this.sectionbox = new SelectionBox_1.SelectionBox();
	        this.page = new Index_1.PageDrawElement(width, height);
	        this.page.render();
	        this.handleClick = this.handleClick.bind(this);
	        this.contextRoot = this.page;
	        Dispatcher_1.EventManager.register("CLICK_EVENT", this.handleClick);
	        Dispatcher_1.EventManager.register("MOUSE_DOWN_EVENT", this.onMouseDown.bind(this));
	        Dispatcher_1.EventManager.register("MOUSE_MOVE_EVENT", this.onMouseMove.bind(this));
	        Dispatcher_1.EventManager.register("MOUSE_UP_EVENT", this.onMouseUp.bind(this));
	        Dispatcher_1.EventManager.register("CONTEXT_MENU_EVENT", this.onContextMenu.bind(this));
	        Dispatcher_1.EventManager.register("IMAGE_UPLOAD_EVENT", this.uploadImage.bind(this));
	        Dispatcher_1.EventManager.register("DESINGER_COMPONENTITEM_EVENT", this.loadComponent.bind(this));
	        Dispatcher_1.EventManager.register("DESIGNER_DOUBLECLICK_EVENT", this.onDoubleClick.bind(this));
	    }
	    Object.defineProperty(EditorPanel.prototype, "editing", {
	        get: function () {
	            return EditorPanel.EditStatus;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    EditorPanel.prototype.handleClick = function (args) {
	        // var iterator = this.page.iterator;
	        var iterator = new DrawElement_1.PostOrderTreeNodeIterator(this.page);
	        var isMultiSelect = args.event.ctrlKey || args.event.metaKey;
	        if (EditorPanel.Dragging == DragStateEnum.Dragging || EditorPanel.SelectionState == SelectionBox_1.SelectionState.Selecting) {
	            EditorPanel.Dragging = DragStateEnum.Origin;
	            EditorPanel.SelectionState = SelectionBox_1.SelectionState.Origin;
	            return;
	        }
	        EditorPanel.SelectionState = SelectionBox_1.SelectionState.Origin;
	        EditorPanel.Dragging = DragStateEnum.Origin;
	        // EventManager.unregister("CLICK_EVENT", this.handleClick);
	        while (!iterator.next().done) {
	            var current = iterator.current;
	            if (current.isActive(args)) {
	                if (this.editing) {
	                    return;
	                }
	                if (!isMultiSelect) {
	                    this.blur();
	                }
	                this.select(current);
	                this.fireSelectedEvent(current);
	                return;
	            }
	        }
	        this.blur();
	        Dispatcher_1.EventManager.broadcast('DESINGNER_BLUR_EVENT', { event: { target: this } });
	    };
	    EditorPanel.prototype.fireSelectedEvent = function (current) {
	        Dispatcher_1.EventManager.broadcast('DESINGER_SELECTED_EVENT', { element: current, event: { target: this } });
	    };
	    EditorPanel.prototype.fireBlurEvent = function () {
	        this.blur();
	        Dispatcher_1.EventManager.broadcast('DESINGNER_BLUR_EVENT', { event: { target: this } });
	    };
	    EditorPanel.prototype.onDoubleClick = function (args) {
	        var target = args.event.target;
	        for (var i = 0; i < this.actived.length; i++) {
	            var item = this.actived[i];
	            if ((item instanceof Index_1.TextDrawElement) && item.control && item.control.isActive(args)) {
	                EditorPanel.EditStatus = true;
	                Dispatcher_1.EventManager.broadcast('DESINGNER_EDITTEXT_EVENT', { text: item, event: { target: this } });
	                return;
	            }
	        }
	    };
	    EditorPanel.prototype.onMouseDown = function (args) {
	        var _this = this;
	        var clientX = args.event.clientX;
	        var clientY = args.event.clientY;
	        var isActive = false;
	        this.actived.forEach(function (current) {
	            if (current.isActive(args)) {
	                current.control && current.control.handle(args.event, _this.actived);
	                EditorPanel.Dragging = DragStateEnum.Start;
	                Dispatcher_1.EventManager.broadcast('DESINGER_DRAGSTART_EVENT', {
	                    clientX: args.event.clientX,
	                    clientY: args.event.clientY,
	                    event: { target: _this }
	                });
	                isActive = true;
	                return false;
	            }
	        });
	        if (!isActive && args.event.button != 2) {
	            EditorPanel.SelectionState = SelectionBox_1.SelectionState.Start;
	            Dispatcher_1.EventManager.broadcast('DESINGER_SELECTION_STRAT_EVENT', { clientX: clientX, clientY: clientY, event: { target: this } });
	        }
	    };
	    EditorPanel.prototype.onMouseMove = function (args) {
	        var clientX = args.event.clientX;
	        var clientY = args.event.clientY;
	        var params = { clientX: clientX, clientY: clientY, event: { target: this } };
	        if (EditorPanel.Dragging > DragStateEnum.Origin) {
	            EditorPanel.Dragging = DragStateEnum.Dragging;
	            Dispatcher_1.EventManager.broadcast('DESINGER_DRAG_EVENT', params);
	            return;
	        }
	        //框选
	        if (EditorPanel.SelectionState > SelectionBox_1.SelectionState.Origin) {
	            EditorPanel.SelectionState = SelectionBox_1.SelectionState.Selecting;
	            Dispatcher_1.EventManager.broadcast('DESINGER_SELECTION_MOVE_EVENT', params);
	        }
	    };
	    EditorPanel.prototype.onMouseUp = function (args) {
	        var clientX = args.event.clientX;
	        var clientY = args.event.clientY;
	        var params = { clientX: clientX, clientY: clientY, event: { target: this } };
	        // console.log('onMouseUp onMouseUp onMouseUp')  
	        if (EditorPanel.Dragging > DragStateEnum.Origin) {
	            Dispatcher_1.EventManager.broadcast('DESINGER_DRAGEND_EVENT', params);
	            return;
	        }
	        if (EditorPanel.SelectionState > SelectionBox_1.SelectionState.Origin) {
	            Dispatcher_1.EventManager.broadcast('DESINGER_SELECTION_END_EVENT', params);
	            if (!this.sectionbox.valid) {
	                EditorPanel.SelectionState = SelectionBox_1.SelectionState.Origin;
	            }
	        }
	    };
	    //鼠标右键
	    EditorPanel.prototype.onContextMenu = function (args) {
	        var isActive = this.actived.length > 0 ? true : false;
	        args.isActive = isActive;
	        args.target = this;
	        Dispatcher_1.EventManager.broadcast('DESINGER_CONTEXTMENU_SHOW_EVENT', args);
	    };
	    EditorPanel.prototype.select = function (current) {
	        current = current.forElement || current;
	        this.actived.push(current);
	        current.active();
	    };
	    EditorPanel.prototype.blur = function () {
	        EditorPanel.EditStatus = false;
	        this.actived.forEach(function (item) {
	            item.blur();
	        });
	        this.actived = [];
	    };
	    /**
	     * 插入文本
	     * orientation： 水平文本 垂直文本
	     */
	    EditorPanel.prototype.insertText = function (orientation) {
	        var text = new Index_1.TextDrawElement(orientation);
	        var layout = new Index_1.LayoutDrawElement();
	        //文本元素在插入表格中，需要去掉layout
	        if (this.actived.length == 1 && this.actived[0] instanceof Index_1.TableCellDrawElement) {
	            this.add(text);
	            return text;
	        }
	        else {
	            layout.layoutFor(text);
	            this.add(layout);
	            return layout;
	        }
	    };
	    /**
	     * 插入矩形
	     */
	    EditorPanel.prototype.insertRect = function () {
	        var text = new Index_1.RectDrawElement();
	        var layout = new Index_1.LayoutDrawElement();
	        layout.layoutFor(text);
	        this.add(layout);
	        return layout;
	    };
	    /**
	     * 插入组件占位符
	     */
	    EditorPanel.prototype.insertComponent = function () {
	        var component = new Index_1.ComponentDrawElement();
	        this.add(component);
	        return component;
	    };
	    /**
	     * 插入线条
	     * orientation： 水平文本 垂直文本
	     */
	    EditorPanel.prototype.insertLine = function (orientation) {
	        var line = new Index_1.LineDrawElement(orientation);
	        this.add(line);
	        return line;
	    };
	    /**
	     * 插入表格
	     * col 行数
	     * row 列数
	     */
	    EditorPanel.prototype.insertTable = function (row, col) {
	        // var col = 3;
	        // var row = 3;
	        var table = new Index_1.TableDrawElement(this.page.width, col, row);
	        var layout = new Index_1.LayoutDrawElement();
	        layout.layoutFor(table);
	        this.add(layout);
	        return layout;
	    };
	    /**
	     * 插入图片
	     */
	    EditorPanel.prototype.insertImage = function () {
	        // console.info('insertImage');
	        var args = {
	            isActive: true,
	            target: this
	        };
	        Dispatcher_1.EventManager.broadcast('DESINGER_UPLOAD_DAILOG_SHOW_EVENT', args);
	    };
	    EditorPanel.prototype.uploadImage = function (args) {
	        var _this = this, files = args.event.target.files[0];
	        var formDOM = document.getElementById("J_UploadForm");
	        var formData = new FormData(formDOM);
	        Server.upload('waybill.upload.image', formData).then(function (response) {
	            // console.log(response);
	            if (response["success"]) {
	                var imgsrc = response['data'][0]['file_url'];
	                var image = new Image();
	                image.src = imgsrc;
	                image.onload = function () {
	                    //单位转换
	                    var imgSize = {
	                        width: Index_2.Unit.toMillimeter(image.width),
	                        height: Index_2.Unit.toMillimeter(image.height)
	                    };
	                    var text = new Index_1.ImageDrawElement(imgsrc, 10, 10, imgSize.width, imgSize.height, _this.page);
	                    var layout = new Index_1.LayoutDrawElement();
	                    layout.layoutFor(text);
	                    _this.add(layout);
	                    return layout;
	                };
	                Dispatcher_1.EventManager.broadcast('DESINGER_UPLOAD_DAILOG_STATUS_EVENT', response);
	            }
	            else {
	                Dispatcher_1.EventManager.broadcast('DESINGER_UPLOAD_DAILOG_STATUS_EVENT', response);
	            }
	        });
	    };
	    EditorPanel.prototype.insertQrcode = function (type) {
	        // console.info('insertQrcode');
	        var text = new Index_1.QrcodeDrawElement(null, null, null, 30, 30, this.page);
	        var layout = new Index_1.LayoutDrawElement();
	        layout.layoutFor(text);
	        this.add(layout);
	        return layout;
	    };
	    EditorPanel.prototype.insertBarcode = function (type) {
	        // console.info('insertBarcode');
	        var text = new Index_1.BarcodeDrawElement(null, null, null, null, null, this.page);
	        var layout = new Index_1.LayoutDrawElement();
	        layout.layoutFor(text);
	        this.add(layout);
	        return layout;
	    };
	    EditorPanel.prototype.render = function (rootDOM) {
	        //var rootDOM = document.getElementById('CNPrintDesigner_DrawPanel');
	        rootDOM.appendChild(this.page.getDrawObject().getDrawContext());
	        rootDOM.parentNode.appendChild(this.sectionbox.renderNode);
	        Dispatcher_1.EventManager.broadcast('DESINGER_READY_EVENT', { event: { target: this } });
	    };
	    EditorPanel.prototype.reset = function (rootElement) {
	        var _a = this.page, width = _a.width, height = _a.height;
	        var pageDrawContext = this.page.getDrawObject().getDrawContext();
	        var rootDOM = pageDrawContext.parentNode;
	        rootDOM.removeChild(pageDrawContext);
	        if (rootElement instanceof Index_1.PageDrawElement) {
	            this.page = rootElement;
	            this.page.render();
	        }
	        else if (rootElement instanceof Index_1.LayoutDrawElement) {
	            this.page = new Index_1.PageDrawElement(width, height);
	            this.page.render();
	            this.page.addChild(rootElement);
	        }
	        pageDrawContext = this.page.getDrawObject().getDrawContext();
	        rootDOM.appendChild(pageDrawContext);
	        rootDOM.parentNode.appendChild(this.sectionbox.renderNode);
	    };
	    EditorPanel.prototype.remove = function (element) {
	        var parent = element.parent;
	        // if(parent.forElement && parent.forElement.id == element.id ){
	        //     (parent.parent as DrawElement).removeChild(parent);
	        //     element = parent;
	        // }else{
	        //      element.parent.removeChild(element);
	        // }
	        if (element.parent instanceof TableRowDrawElement_1.TableRowDrawElement) {
	            var index = element.parent.children.indexOf(element);
	            element.parent.removeCell(index);
	        }
	        else {
	            element.parent.removeChild(element);
	        }
	        Dispatcher_1.EventManager.broadcast('DESINGER_REMOVE_EVENT', { element: element, event: { target: this } });
	        this.blur();
	        Dispatcher_1.EventManager.broadcast('DESINGNER_BLUR_EVENT', { element: element, event: { target: this } });
	    };
	    EditorPanel.prototype.getContextRoot = function (rootElement) {
	        var contextRoot = rootElement;
	        if (rootElement instanceof Index_1.LayoutDrawElement) {
	            return contextRoot;
	        }
	        for (var i = 0; i < rootElement.children.length; i++) {
	            if (rootElement.children[i] instanceof Index_1.LayoutDrawElement) {
	                var rootLayoutElement = rootElement.children[i];
	                if (!rootLayoutElement.forElement) {
	                    contextRoot = rootLayoutElement;
	                }
	                break;
	            }
	        }
	        return contextRoot;
	    };
	    EditorPanel.prototype.load = function (template) {
	        // template = template.replace(/(^\s*)|(\s*$)/g,"");
	        var parser = new TemplateParser_1.default();
	        var rootElement = parser.parse(template);
	        this.contextRoot = this.getContextRoot(rootElement);
	        this.reset(rootElement);
	        Dispatcher_1.EventManager.broadcast('DESINGNER_LOADED_EVENT', { event: { target: this } });
	        for (var i = 0; i < this.contextRoot.children.length; i++) {
	            if (this.contextRoot.children[i] instanceof Index_1.LineDrawElement) {
	                var elem = this.contextRoot.children[i].getDrawObject().getDrawContext();
	                elem.style.zIndex = i + '';
	            }
	        }
	    };
	    EditorPanel.prototype.loadComponent = function (response) {
	        var _this = this;
	        //    response ={"code":"0","data":[{"name":"组合打印项","content":"<?xml version=\"1.0\" encoding=\"UTF-8\"?> <page         xmlns=\"http:\/\/cloudprint.cainiao.com\/print\"         xmlns:xsi=\"http:\/\/www.w3.org\/2001\/XMLSchema-instance\"         xsi:schemaLocation=\"http:\/\/cloudprint.cainiao.com\/print http:\/\/cloudprint-docs-resource.oss-cn-shanghai.aliyuncs.com\/lpml_schema.xsd\"         xmlns:editor=\"http:\/\/cloudprint.cainiao.com\/schema\/editor\"         width=\"100\" height=\"40\" >   <layout              editor:_for_=\"1487145062097464\"             id=\"1487123078139751\" width=\"21.03\" height=\"12.94\" left=\"22.09\" top=\"7.04\"  style=\"zIndex:1;\">             <text editor:_printName_=\"商品名称\"                  style=\"fontSize:8;orientation:horizontal;alpha:1;                 letterSpacing:0;                 fontFamily:SimHei;                 fontWeight:normal;                 align:center;                 valign:middle;                 fontUnderline:false;                 fontItalic:false;                 direction:ltr\">                 <![CDATA[<%=name%>]]>             <\/text>            <\/layout>   <layout              editor:_for_=\"1487145062098863\"             id=\"1487123173830340\" width=\"21.03\" height=\"12.94\" left=\"53\" top=\"7.04\"  style=\"zIndex:2;\">             <text editor:_printName_=\"商品数量\"                  style=\"fontSize:8;orientation:horizontal;alpha:1;                 letterSpacing:0;                 fontFamily:SimHei;                 fontWeight:normal;                 align:center;                 valign:middle;                 fontUnderline:false;                 fontItalic:false;                 direction:ltr\">                 <![CDATA[<%=count%>]]>             <\/text>            <\/layout>   <layout              editor:_for_=\"148714506209854\"             id=\"1487123192938556\" width=\"9.39\" height=\"13.21\" left=\"43.33\" top=\"6.56\"  style=\"zIndex:3;\">             <text editor:_printName_=\"-\"                  style=\"fontSize:8;orientation:horizontal;alpha:1;                 letterSpacing:0;                 fontFamily:SimHei;                 fontWeight:normal;                 align:center;                 valign:middle;                 fontUnderline:false;                 fontItalic:false;                 direction:ltr\">                 <![CDATA[-]]>             <\/text>            <\/layout> <\/page>","biz_component_id":"147607"}],"success":true}
	        var template = response.data[0].content;
	        var parser = new TemplateParser_1.default();
	        var rootElement = parser.parse(template);
	        var replaceElement = (this.actived[0] instanceof Index_1.ComponentDrawElement) ? this.actived[0] : null;
	        if (rootElement.dataset) {
	            rootElement.component = true;
	            var component = parser.parse(rootElement.getTemplate());
	            component.component = true;
	            if (replaceElement) {
	                component.left = replaceElement.left;
	                component.top = replaceElement.top;
	            }
	            if (rootElement.data) {
	                (component.forElement).data = rootElement.data;
	            }
	            this.add(component);
	            if (replaceElement) {
	                replaceElement.parent.removeChild(replaceElement);
	            }
	            return;
	        }
	        var wrapper = null;
	        if (rootElement.children.length > 1) {
	            wrapper = new Index_1.LayoutDrawElement();
	            if (replaceElement) {
	                wrapper.left = replaceElement.left;
	                wrapper.top = replaceElement.top;
	            }
	        }
	        for (var i = 0; i < rootElement.children.length; i++) {
	            var componentElement = rootElement.children[i];
	            if (componentElement instanceof Index_1.LayoutDrawElement) {
	                componentElement.status = "";
	            }
	            componentElement.component = true;
	            //处理表格打印项
	            if (componentElement.forElement instanceof Index_1.TableDrawElement) {
	                componentElement = parser.parse(componentElement.getTemplate());
	                componentElement.component = true;
	                componentElement.forElement.dataset = null;
	            }
	            if (wrapper) {
	                wrapper.addChild(componentElement);
	                continue;
	            }
	            if (replaceElement) {
	                componentElement.left = replaceElement.left;
	                componentElement.top = replaceElement.top;
	            }
	            this.add(componentElement);
	        }
	        if (wrapper) {
	            this.add(wrapper);
	        }
	        if (replaceElement) {
	            replaceElement.parent.removeChild(replaceElement);
	        }
	    };
	    EditorPanel.prototype.add = function (element) {
	        var context = this.contextRoot;
	        if (element.parent && !element.component) {
	            context = element.parent;
	        }
	        else if (this.actived.length == 1 && this.actived[0] instanceof Index_1.TableCellDrawElement) {
	            context = this.actived[0];
	            element.left = 0.2;
	            element.top = 0.2;
	        }
	        context.addChild(element);
	        Dispatcher_1.EventManager.broadcast('DESINGER_ADD_EVENT', { element: element, event: { target: this } });
	        this.blur();
	        this.select(element);
	        Dispatcher_1.EventManager.broadcast('DESINGER_SELECTED_EVENT', { element: element, event: { target: this } });
	        Dispatcher_1.Dispatcher.dispatch({
	            type: 'ZINDEX_COMMAND',
	            data: {
	                arrange: Enum_1.Arrange.Front
	            }
	        });
	    };
	    //缩放大小
	    EditorPanel.prototype.zoom = function () {
	        this.page.render();
	        var iterator = this.page.iterator;
	        while (!iterator.next().done) {
	            var current = iterator.current;
	            if (current instanceof Index_1.LayoutDrawElement) {
	                continue;
	            }
	            current.update();
	        }
	        Dispatcher_1.EventManager.broadcast('DESIGNER_ZOOM_EVENT', { scale: Index_2.Unit.scale, event: { target: this } });
	    };
	    EditorPanel.prototype.getTemplate = function () {
	        var xml = '<?xml version="1.0" encoding="UTF-8"?>\n';
	        if (this.page.firstChild && this.page.firstChild.id == "CUSTOM_AREA") {
	            return xml + this.page.firstChild.getTemplate();
	        }
	        return xml + this.page.getTemplate();
	    };
	    return EditorPanel;
	}());
	EditorPanel.Dragging = DragStateEnum.Origin;
	EditorPanel.SelectionState = SelectionBox_1.SelectionState.Origin;
	EditorPanel.EditStatus = false;
	exports.EditorPanel = EditorPanel;


/***/ }),
/* 23 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	// declare var Reflect:any;
	var TreeNode_1 = __webpack_require__(24);
	exports.PostOrderTreeNodeIterator = TreeNode_1.PostOrderTreeNodeIterator;
	var EventManager_1 = __webpack_require__(5);
	var DrawStatus_1 = __webpack_require__(25);
	exports.DrawStatus = DrawStatus_1.DrawStatus;
	var Index_1 = __webpack_require__(16);
	var DrawElement = (function (_super) {
	    __extends(DrawElement, _super);
	    function DrawElement() {
	        var _this = _super.call(this) || this;
	        _this.component = false;
	        _this.rotation = 0;
	        _this.id = Index_1.GUID();
	        return _this;
	    }
	    DrawElement.prototype.getDrawContext = function () {
	        if (this.getDrawObject()) {
	            return this.getDrawObject().getDrawContext();
	        }
	        return null;
	    };
	    //获取元素的属性检查器
	    DrawElement.prototype.getInspectorName = function () {
	        if (this.forElement) {
	            return this.forElement.constructor.name + 'Inspector';
	        }
	        return this.constructor.name + 'Inspector';
	    };
	    DrawElement.prototype.addChild = function (child, index) {
	        _super.prototype.addChild.call(this, child, index);
	        child.render();
	    };
	    DrawElement.prototype.removeChild = function (child, context) {
	        _super.prototype.removeChild.call(this, child);
	        child.getDrawObject().remove();
	        child.control && child.control.remove();
	    };
	    //属性变更
	    DrawElement.prototype.update = function (props) {
	        var _this = this;
	        props && props.forEach(function (prop) {
	            _this[prop.propertyName] = prop.propertyValue;
	        });
	        //Reflect.set(this, props.propertyName, props.propertyValue);
	        EventManager_1.default.broadcast('PROPERTY_CHANGE_EVENT', { event: { target: this } });
	        this.render();
	    };
	    //显示
	    DrawElement.prototype.render = function () {
	        this.getDrawObject().draw(this);
	        this.control && this.control.draw(this);
	    };
	    // 判断选中
	    DrawElement.prototype.isActive = function (args) {
	        return this.getDrawObject().isActive(args) || (this.control && this.control.isActive(args));
	    };
	    //选中
	    DrawElement.prototype.active = function () {
	        this.control && this.control.show();
	    };
	    //取消选中
	    DrawElement.prototype.blur = function () {
	        this.control && this.control.hide();
	    };
	    //对象深拷贝
	    DrawElement.prototype.clone = function () {
	        var _this = this;
	        var cloned = Object.create(this);
	        Object.getOwnPropertyNames(this).forEach((function (name) {
	            cloned[name] = _this[name];
	        }).bind(this));
	        cloned.id = Index_1.GUID();
	        return cloned;
	    };
	    return DrawElement;
	}(TreeNode_1.TreeNode));
	exports.DrawElement = DrawElement;
	var DrawObject = (function () {
	    function DrawObject() {
	    }
	    DrawObject.prototype.getDrawContext = function () {
	        return this.renderNode;
	    };
	    DrawObject.prototype.remove = function () {
	        var drawContext = this.getDrawContext();
	        this.status = "removed";
	        if (drawContext) {
	            drawContext.parentNode.removeChild(drawContext);
	        }
	    };
	    ;
	    return DrawObject;
	}());
	exports.DrawObject = DrawObject;


/***/ }),
/* 24 */
/***/ (function(module, exports) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	//队列结构
	var Queue = (function () {
	    function Queue() {
	        this.list = [];
	    }
	    Queue.prototype.enqueue = function (element) {
	        this.list.push(element);
	    };
	    Queue.prototype.dequeue = function () {
	        return this.list.shift();
	    };
	    //返回栈顶元素
	    Queue.prototype.peek = function () {
	        return this.list[0];
	    };
	    Object.defineProperty(Queue.prototype, "count", {
	        get: function () {
	            return this.list.length;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    return Queue;
	}());
	exports.Queue = Queue;
	//前序遍历
	var TreeNodeIterator = (function () {
	    function TreeNodeIterator(root) {
	        this.root = root;
	        this.collection = new Queue();
	        this.fillQueue(root);
	    }
	    TreeNodeIterator.prototype.fillQueue = function (treeNode) {
	        this.collection.enqueue(treeNode);
	        if (treeNode.children != null && treeNode.children.length > 0) {
	            for (var _i = 0, _a = treeNode.children; _i < _a.length; _i++) {
	                var child = _a[_i];
	                // this.collection.enqueue(child);
	                this.fillQueue(child);
	            }
	        }
	    };
	    // [Symbol.iterator]():Iterator<TreeNode> {
	    // 	return this;
	    // }
	    TreeNodeIterator.prototype.next = function () {
	        var done = this.collection.count < 1;
	        this.current = this.collection.dequeue();
	        return {
	            done: done,
	            value: this.current
	        };
	    };
	    return TreeNodeIterator;
	}());
	var TreeNode = (function () {
	    function TreeNode() {
	        //this.id = (new Date()).getTime().toString();
	        this.children = [];
	    }
	    //是否叶子节点
	    TreeNode.prototype.isLeaf = function () {
	        return this.children.length > 0;
	    };
	    Object.defineProperty(TreeNode.prototype, "firstChild", {
	        //获取第一个子节点
	        get: function () {
	            return this.children[0];
	        },
	        enumerable: true,
	        configurable: true
	    });
	    //添加子节点
	    TreeNode.prototype.addChild = function (child, index) {
	        child.parent = this;
	        if (index !== undefined) {
	            this.children.splice(index, 0, child);
	        }
	        else {
	            this.children.push(child);
	        }
	    };
	    //移除子节点
	    TreeNode.prototype.removeChild = function (child, context) {
	        var i = this.children.indexOf(child);
	        if (i > -1) {
	            this.children.splice(i, 1);
	        }
	    };
	    //查找自定节点
	    TreeNode.prototype.find = function (id) {
	        var iterator = this.iterator;
	        while (!iterator.next().done) {
	            if (iterator.current.id == id) {
	                return iterator.current;
	            }
	        }
	        return null;
	    };
	    Object.defineProperty(TreeNode.prototype, "iterator", {
	        //迭代器，遍历TreeNode
	        get: function () {
	            return new TreeNodeIterator(this);
	        },
	        enumerable: true,
	        configurable: true
	    });
	    ;
	    return TreeNode;
	}());
	exports.TreeNode = TreeNode;
	//栈
	var Stack = (function () {
	    function Stack() {
	        this.list = [];
	    }
	    //进栈
	    Stack.prototype.push = function (element) {
	        this.list.push(element);
	    };
	    //出栈
	    Stack.prototype.pop = function () {
	        return this.list.pop();
	    };
	    //返回栈顶元素
	    Stack.prototype.peek = function () {
	        return this.list[this.list.length - 1];
	    };
	    Object.defineProperty(Stack.prototype, "count", {
	        get: function () {
	            return this.list.length;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    return Stack;
	}());
	//非递归倒叙层次遍历树结构
	var PostOrderTreeNodeIterator = (function () {
	    function PostOrderTreeNodeIterator(root) {
	        this.root = root;
	        this.queue = new Queue();
	        this.stack = new Stack();
	        this.fill(root);
	    }
	    PostOrderTreeNodeIterator.prototype.fill = function (treeNode) {
	        this.queue.enqueue(treeNode);
	        while (this.queue.count > 0) {
	            treeNode = this.queue.peek();
	            for (var _i = 0, _a = treeNode.children; _i < _a.length; _i++) {
	                var child = _a[_i];
	                this.queue.enqueue(child);
	            }
	            this.stack.push(this.queue.dequeue());
	        }
	    };
	    PostOrderTreeNodeIterator.prototype.next = function () {
	        var done = this.stack.count < 1;
	        this.current = this.stack.pop();
	        return {
	            done: done,
	            value: this.current
	        };
	    };
	    return PostOrderTreeNodeIterator;
	}());
	exports.PostOrderTreeNodeIterator = PostOrderTreeNodeIterator;
	exports.default = TreeNode;


/***/ }),
/* 25 */
/***/ (function(module, exports) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	/**
	 * 状态对象
	 */
	var DrawStatus = (function () {
	    function DrawStatus() {
	    }
	    return DrawStatus;
	}());
	exports.DrawStatus = DrawStatus;
	exports.default = DrawStatus;


/***/ }),
/* 26 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	var LayoutDrawElement_1 = __webpack_require__(27);
	exports.LayoutDrawElement = LayoutDrawElement_1.LayoutDrawElement;
	var PageDrawElement_1 = __webpack_require__(34);
	exports.PageDrawElement = PageDrawElement_1.PageDrawElement;
	var RectDrawElement_1 = __webpack_require__(35);
	exports.RectDrawElement = RectDrawElement_1.RectDrawElement;
	var TextDrawElement_1 = __webpack_require__(33);
	exports.TextDrawElement = TextDrawElement_1.TextDrawElement;
	var LineDrawElement_1 = __webpack_require__(36);
	exports.LineDrawElement = LineDrawElement_1.LineDrawElement;
	var ImageDrawElement_1 = __webpack_require__(37);
	exports.ImageDrawElement = ImageDrawElement_1.ImageDrawElement;
	var TableDrawElement_1 = __webpack_require__(29);
	exports.TableDrawElement = TableDrawElement_1.TableDrawElement;
	var TableCellDrawElement_1 = __webpack_require__(32);
	exports.TableCellDrawElement = TableCellDrawElement_1.TableCellDrawElement;
	var BarcodeDrawElement_1 = __webpack_require__(38);
	exports.BarcodeDrawElement = BarcodeDrawElement_1.BarcodeDrawElement;
	var QrcodeDrawElement_1 = __webpack_require__(81);
	exports.QrcodeDrawElement = QrcodeDrawElement_1.QrcodeDrawElement;
	var ScriptDrawElement_1 = __webpack_require__(82);
	exports.ScriptDrawElement = ScriptDrawElement_1.ScriptDrawElement;
	var ComponentDrawElement_1 = __webpack_require__(83);
	exports.ComponentDrawElement = ComponentDrawElement_1.ComponentDrawElement;
	var TableRowDrawElement_1 = __webpack_require__(31);
	exports.TableRowDrawElement = TableRowDrawElement_1.TableRowDrawElement;
	var HeaderDrawElement_1 = __webpack_require__(84);
	exports.HeaderDrawElement = HeaderDrawElement_1.HeaderDrawElement;
	var FooterDrawElement_1 = __webpack_require__(85);
	exports.FooterDrawElement = FooterDrawElement_1.FooterDrawElement;
	var PageIndexDrawElement_1 = __webpack_require__(86);
	exports.PageIndexDrawElement = PageIndexDrawElement_1.PageIndexDrawElement;


/***/ }),
/* 27 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var DrawElement_1 = __webpack_require__(23);
	var Index_1 = __webpack_require__(16);
	var Const_1 = __webpack_require__(28);
	var TableDrawElement_1 = __webpack_require__(29);
	/**
	 * 使用CSS DOM 绘制Layout元素
	 */
	var DOMLayoutDraw = (function () {
	    function DOMLayoutDraw() {
	        this.status = "";
	        this.renderNode = Index_1.DOMUtils.CreateElement('div');
	    }
	    DOMLayoutDraw.prototype.getDrawContext = function () {
	        return this.renderNode;
	    };
	    DOMLayoutDraw.prototype.isActive = function () {
	        return false;
	    };
	    DOMLayoutDraw.prototype.remove = function () {
	        this.status = "removed";
	        if (this.renderNode && this.renderNode.parentNode) {
	            this.renderNode.parentNode.removeChild(this.renderNode);
	        }
	    };
	    DOMLayoutDraw.prototype.draw = function (layoutEelement) {
	        var div = this.renderNode;
	        var parent = layoutEelement.parent;
	        var context = parent.getDrawObject().getDrawContext();
	        var left = layoutEelement.left, top = layoutEelement.top, width = layoutEelement.width, height = layoutEelement.height;
	        div.className = 'element-layout';
	        left = Index_1.Unit.toPixel(left);
	        top = Index_1.Unit.toPixel(top) || Index_1.Unit.toPixel(140);
	        width = Index_1.Unit.toPixel(width);
	        height = Index_1.Unit.toPixel(height);
	        if (layoutEelement.id == 'CUSTOM_AREA') {
	            var placeholder = Index_1.DOMUtils.CreateElement('div');
	            placeholder.setAttribute('id', 'STANDARD_AREA');
	            div.setAttribute('id', layoutEelement.id);
	            Index_1.DOMUtils.ApplyStyle(div, {
	                left: left + 'px',
	                height: height + 'px',
	                width: width + 'px',
	                top: top + 'px',
	                position: 'absolute'
	            });
	            context.appendChild(placeholder);
	        }
	        if (this.status == "" || this.status == "removed") {
	            this.status = 'render';
	            context.appendChild(this.renderNode);
	        }
	    };
	    return DOMLayoutDraw;
	}());
	/**
	 * Layout元素
	 */
	var LayoutDrawElement = (function (_super) {
	    __extends(LayoutDrawElement, _super);
	    function LayoutDrawElement(left, top, width, height) {
	        var _this = _super.call(this) || this;
	        _this._for_ = "";
	        _this.left = left;
	        _this.top = top;
	        _this.width = width;
	        _this.height = height;
	        _this.drawObject = new DOMLayoutDraw();
	        return _this;
	    }
	    LayoutDrawElement.prototype.getDrawObject = function () {
	        return this.drawObject;
	    };
	    LayoutDrawElement.prototype.layoutFor = function (element) {
	        this.forElement = element;
	        this.addChild(element);
	    };
	    LayoutDrawElement.prototype.layout = function (element) {
	        this.forElement = element;
	        element.id = this._for_;
	        element.left = this._left;
	        element.top = this._top;
	        element.width = this._width;
	        element.height = this._height;
	    };
	    Object.defineProperty(LayoutDrawElement.prototype, "zIndex", {
	        get: function () {
	            if (this.forElement) {
	                return this.forElement.zIndex;
	            }
	            return this._zIndex;
	        },
	        set: function (value) {
	            if (this.forElement) {
	                this.forElement.zIndex = value;
	                return;
	            }
	            this._zIndex = value;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    Object.defineProperty(LayoutDrawElement.prototype, "width", {
	        get: function () {
	            return this.forElement ? this.forElement.width : this._width;
	        },
	        set: function (value) {
	            if (this.forElement) {
	                this.forElement.width = value;
	            }
	            this._width = value;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    Object.defineProperty(LayoutDrawElement.prototype, "height", {
	        get: function () {
	            if (this.forElement instanceof TableDrawElement_1.TableDrawElement) {
	                return this.forElement ? this.forElement._height : this._height;
	            }
	            return this.forElement ? this.forElement.height : this._height;
	        },
	        set: function (value) {
	            if (this.forElement) {
	                this.forElement.height = value;
	            }
	            this._height = value;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    Object.defineProperty(LayoutDrawElement.prototype, "left", {
	        get: function () {
	            if (this.forElement) {
	                return this.forElement.left;
	            }
	            else {
	                return this._left;
	            }
	        },
	        set: function (value) {
	            if (this.forElement) {
	                this.forElement.left = value;
	            }
	            else {
	                this._left = value;
	            }
	        },
	        enumerable: true,
	        configurable: true
	    });
	    Object.defineProperty(LayoutDrawElement.prototype, "top", {
	        get: function () {
	            if (this.forElement) {
	                return this.forElement.top;
	            }
	            else {
	                return this._top;
	            }
	        },
	        set: function (value) {
	            if (this.forElement) {
	                this.forElement.top = value;
	            }
	            else {
	                this._top = value;
	            }
	        },
	        enumerable: true,
	        configurable: true
	    });
	    Object.defineProperty(LayoutDrawElement.prototype, "component", {
	        get: function () {
	            if (this.forElement) {
	                return this.forElement.component;
	            }
	            return false;
	        },
	        set: function (value) {
	            if (this.forElement) {
	                this.forElement.component = value;
	            }
	        },
	        enumerable: true,
	        configurable: true
	    });
	    Object.defineProperty(LayoutDrawElement.prototype, "status", {
	        get: function () {
	            var drawObject = this.drawObject;
	            return drawObject.status;
	        },
	        set: function (value) {
	            var drawObject = this.drawObject;
	            drawObject.status = "";
	        },
	        enumerable: true,
	        configurable: true
	    });
	    LayoutDrawElement.prototype.clone = function () {
	        var layout = new LayoutDrawElement();
	        if (this.forElement) {
	            layout.layoutFor(this.forElement.clone());
	        }
	        return layout;
	    };
	    Object.defineProperty(LayoutDrawElement.prototype, "control", {
	        get: function () {
	            if (this.forElement) {
	                return this.forElement.control;
	            }
	            return null;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    LayoutDrawElement.prototype.render = function () {
	        this.getDrawObject().draw(this);
	    };
	    LayoutDrawElement.prototype.getMetadata = function (key) {
	        var metadata = {
	            left: {
	                value: this.left,
	                type: Number
	            },
	            top: {
	                value: this.top,
	                type: Number
	            },
	            width: {
	                value: this.width,
	                type: Number
	            },
	            height: {
	                value: this.height,
	                type: Number
	            },
	            orientation: {
	                value: this.orientation
	            },
	            overflow: {
	                value: this.overflow
	            }
	        };
	        return metadata[key];
	    };
	    LayoutDrawElement.prototype.getComputedStyle = function (key) {
	        var propertyMetadata = this.getMetadata(key);
	        if (propertyMetadata.defaultValue == propertyMetadata.value) {
	            return '';
	        }
	        return key + ":" + propertyMetadata.value + ";";
	    };
	    LayoutDrawElement.prototype.getComputedProperty = function (key) {
	        var propertyMetadata = this.getMetadata(key);
	        if (propertyMetadata.defaultValue == propertyMetadata.value) {
	            return '';
	        }
	        return key + "=\"" + propertyMetadata.value + "\" ";
	    };
	    LayoutDrawElement.prototype.getTemplate = function () {
	        var template = "";
	        var childrenTemplate = [];
	        var props = [
	            this.getComputedProperty('width'),
	            this.getComputedProperty('height'),
	            this.getComputedProperty('left'),
	            this.getComputedProperty('top'),
	            this.getComputedProperty('orientation')
	        ];
	        var customArea = "";
	        var _for_ = this.forElement && this.forElement.id ? 'editor:_for_="' + this.forElement.id + '"' : '';
	        var zIndex = this.zIndex ? "zIndex:" + this.zIndex + ";" : '';
	        var margin = this.margin ? "margin:" + this.margin + ";" : '';
	        var padding = this.padding ? "padding:" + this.padding + ";" : '';
	        var ref = this.ref ? "ref=\"" + this.ref + "\"" : '';
	        var styles = [this.getComputedStyle('overflow')];
	        var style = "";
	        if (zIndex) {
	            styles.push(zIndex);
	        }
	        if (margin) {
	            styles.push(margin);
	        }
	        if (padding) {
	            styles.push(padding);
	        }
	        if (styles.length) {
	            style = "style=\"" + styles.join('') + "\"";
	        }
	        for (var _i = 0, _a = this.children; _i < _a.length; _i++) {
	            var child = _a[_i];
	            var childElement = child;
	            childrenTemplate.push(childElement.getTemplate());
	        }
	        template = "<layout \n            " + _for_ + "\n            " + ref + "\n            id=\"" + this.id + "\" " + props.join('') + " " + style + ">" + childrenTemplate.join('') + "\n\t\t</layout>";
	        if (this.id == 'CUSTOM_AREA') {
	            customArea = "\n            xmlns=\"" + Const_1.CLOUD_PRINT_NAME_SPACE + "\"\n            xmlns:xsi=\"" + Const_1.XSI_NAME_SPACE + "\"\n            xsi:schemaLocation=\"" + Const_1.SCHEMA_LOCATION_NAME_SPACE + "\"\n            xmlns:editor=\"" + Const_1.EDITOR_NAME_SPACE + "\"";
	            template = "<layout \n                " + customArea + "\n                " + _for_ + "\n                " + ref + "\n                id=\"" + this.id + "\" " + props.join('') + " >\n\t\t" + childrenTemplate.join('\n\t\t') + "\n</layout>";
	        }
	        return template;
	    };
	    LayoutDrawElement.prototype.isActive = function (args) {
	        return false;
	    };
	    return LayoutDrawElement;
	}(DrawElement_1.DrawElement));
	exports.LayoutDrawElement = LayoutDrawElement;


/***/ }),
/* 28 */
/***/ (function(module, exports) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	var EDITOR_NAME_SPACE = 'http://cloudprint.cainiao.com/schema/editor';
	exports.EDITOR_NAME_SPACE = EDITOR_NAME_SPACE;
	var TEXT_PLACE_HOLDER = '请输入内容';
	exports.TEXT_PLACE_HOLDER = TEXT_PLACE_HOLDER;
	var XSI_NAME_SPACE = 'http://www.w3.org/2001/XMLSchema-instance';
	exports.XSI_NAME_SPACE = XSI_NAME_SPACE;
	var SCHEMA_LOCATION_NAME_SPACE = 'http://cloudprint.cainiao.com/print http://cloudprint-docs-resource.oss-cn-shanghai.aliyuncs.com/lpml_schema.xsd';
	exports.SCHEMA_LOCATION_NAME_SPACE = SCHEMA_LOCATION_NAME_SPACE;
	var CLOUD_PRINT_NAME_SPACE = 'http://cloudprint.cainiao.com/print';
	exports.CLOUD_PRINT_NAME_SPACE = CLOUD_PRINT_NAME_SPACE;
	var CUSTOM_AREA_HEIGHT = 40;
	exports.CUSTOM_AREA_HEIGHT = CUSTOM_AREA_HEIGHT;
	var WAYBILL_WIDTH = 100;
	exports.WAYBILL_WIDTH = WAYBILL_WIDTH;
	var WAYBILL_STANDARD_AREA_HEIGHT = 140;
	exports.WAYBILL_STANDARD_AREA_HEIGHT = WAYBILL_STANDARD_AREA_HEIGHT;


/***/ }),
/* 29 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var DrawElement_1 = __webpack_require__(23);
	var Index_1 = __webpack_require__(16);
	var ControlHandle_1 = __webpack_require__(30);
	var TableRowDrawElement_1 = __webpack_require__(31);
	var TableCellDrawElement_1 = __webpack_require__(32);
	var TextDrawElement_1 = __webpack_require__(33);
	var LayoutDrawElement_1 = __webpack_require__(27);
	var Index_2 = __webpack_require__(2);
	var TableControlHandle = (function (_super) {
	    __extends(TableControlHandle, _super);
	    function TableControlHandle() {
	        return _super !== null && _super.apply(this, arguments) || this;
	    }
	    Object.defineProperty(TableControlHandle.prototype, "controlHandleForCell", {
	        get: function () {
	            if (!this._controlHandleForCell) {
	                var table = this.contextElement;
	                this._controlHandleForCell = TableControlHandle.CreateControlForCell(table);
	            }
	            return this._controlHandleForCell;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    TableControlHandle.prototype.isActive = function (args) {
	        var target = args.event.target;
	        var result = target == this.renderNode ||
	            target.parentNode == this.renderNode ||
	            target == this.controlHandleForCell;
	        return result;
	    };
	    TableControlHandle.CreateControlForCell = function (table) {
	        var tableControl = Index_1.DOMUtils.CreateElement('div', { className: 'table-control' });
	        var tableContainer = table.parent;
	        var context = tableContainer.getDrawObject().getDrawContext();
	        Index_1.DOMUtils.ApplyStyle(tableControl, {
	            left: Index_1.Unit.toPixel(table.left) + "px",
	            top: Index_1.Unit.toPixel(table.top) + "px"
	        });
	        context.appendChild(tableControl);
	        return tableControl;
	    };
	    TableControlHandle.prototype.hide = function () {
	        _super.prototype.hide.call(this);
	        this.hideControlHandleForCell();
	    };
	    TableControlHandle.prototype.hideControlHandleForCell = function () {
	        Index_1.DOMUtils.ApplyStyle(this.controlHandleForCell, {
	            display: 'none'
	        });
	    };
	    TableControlHandle.prototype.show = function () {
	        _super.prototype.show.call(this);
	        this.showControlHandleForCell();
	    };
	    TableControlHandle.prototype.showControlHandleForCell = function () {
	        var table = this.contextElement;
	        Index_1.DOMUtils.ApplyStyle(this.controlHandleForCell, {
	            display: 'block',
	            left: Index_1.Unit.toPixel(table.left) + 'px',
	            top: Index_1.Unit.toPixel(table.top) + 'px'
	        });
	    };
	    TableControlHandle.prototype.render = function () {
	        _super.prototype.render.call(this);
	    };
	    TableControlHandle.prototype.draw = function (drawElement) {
	        _super.prototype.draw.call(this, drawElement);
	        Index_1.DOMUtils.ApplyStyle(this.controlHandleForCell, {
	            left: Index_1.Unit.toPixel(drawElement.left) + 'px',
	            top: Index_1.Unit.toPixel(drawElement.top) + 'px'
	        });
	    };
	    return TableControlHandle;
	}(ControlHandle_1.DOMControlHandle));
	exports.TableControlHandle = TableControlHandle;
	/**
	 * 表格
	 */
	var DOMTableDraw = (function () {
	    function DOMTableDraw() {
	        this.renderNode = Index_1.DOMUtils.CreateElement('table', { className: 'element-table' });
	    }
	    DOMTableDraw.prototype.getDrawContext = function () {
	        return this.renderNode;
	    };
	    DOMTableDraw.prototype.isActive = function (args) {
	        var target = args.event.target;
	        var ret = target == this.renderNode ||
	            this.renderNode.parentNode == target ||
	            this.renderNode.contains(target);
	        return ret;
	    };
	    DOMTableDraw.prototype.remove = function () {
	        this.status = "removed";
	        if (this.renderNode && this.renderNode.parentNode) {
	            this.renderNode.parentNode.removeChild(this.renderNode);
	        }
	    };
	    DOMTableDraw.prototype.draw = function (table) {
	        var parent = table.parent;
	        var renderContext = parent && parent.getDrawObject().getDrawContext();
	        var width = Index_1.Unit.toPixel(table.width) + 'px';
	        var height = Index_1.Unit.toPixel(table.height) + 'px';
	        var left = Index_1.Unit.toPixel(table.left) + 'px';
	        var top = Index_1.Unit.toPixel(table.top) + 'px';
	        var borderWidth = table.borderWidth + 'pt';
	        var position = 'absolute';
	        var zIndex = table.zIndex;
	        this.renderNode.className = 'element-table';
	        if (table.cellBorderWidth == 0 && table.borderWidth == "0") {
	            this.renderNode.className = 'element-table element-table-border-hidden';
	        }
	        this.status = "rendered";
	        Index_1.DOMUtils.ApplyStyle(this.renderNode, {
	            zIndex: zIndex,
	            borderWidth: borderWidth,
	            height: height,
	            width: width,
	            left: left,
	            top: top,
	            position: position
	        });
	        if (renderContext) {
	            renderContext.appendChild(this.renderNode);
	        }
	    };
	    return DOMTableDraw;
	}());
	var TableDrawElement = (function (_super) {
	    __extends(TableDrawElement, _super);
	    function TableDrawElement(width, col, row) {
	        var _this = _super.call(this) || this;
	        _this.borderWidth = "1";
	        _this.cellBorderWidth = 1;
	        _this.zIndex = 0;
	        _this.left = 0;
	        _this.top = 0;
	        _this.rows = [];
	        _this.preRow = [];
	        _this.preColumns = [];
	        _this.configable = true;
	        _this.dataset = null;
	        _this.thead = true;
	        _this.script = false;
	        _this.isTable = true;
	        col = col || 0;
	        row = row || 0;
	        _this.control = new TableControlHandle(_this);
	        _this.drawObject = new DOMTableDraw();
	        _this.width = width;
	        //行
	        for (var i = 0; i < row; i++) {
	            var row_1 = new TableRowDrawElement_1.TableRowDrawElement();
	            _this.addRow(row_1);
	            //列
	            for (var j = 0; j < col; j++) {
	                var cell = new TableCellDrawElement_1.TableCellDrawElement();
	                row_1.addCell(cell);
	            }
	        }
	        Index_2.EventManager.register('TABLE_ADJUST_COL_EVENT', _this.onTableAdjustCol.bind(_this));
	        return _this;
	    }
	    TableDrawElement.prototype.addRow = function (row, index) {
	        this.addChild(row, index);
	        if (!(row instanceof TableRowDrawElement_1.TableRowDrawElement)) {
	            return;
	        }
	        row.table = this;
	        if (index === undefined) {
	            this.rows.push(row);
	        }
	        else {
	            this.rows.splice(index, 0, row);
	        }
	        this.resetRowIndex();
	    };
	    TableDrawElement.prototype.resetRowIndex = function () {
	        for (var i = 0; i < this.rows.length; i++) {
	            this.rows[i].index = i;
	        }
	    };
	    TableDrawElement.prototype.addColumn = function (index) {
	        for (var i = 0; i < this.rows.length; i++) {
	            var cell = new TableCellDrawElement_1.TableCellDrawElement();
	            this.rows[i].addCell(cell, index);
	        }
	    };
	    TableDrawElement.prototype.removeColumn = function (index) {
	        // console.log("addColumn:" + index);
	        for (var i = 0; i < this.rows.length; i++) {
	            var row = this.rows[i];
	            if (row instanceof TableRowDrawElement_1.TableRowDrawElement) {
	                this.rows[i].removeCell(index);
	            }
	        }
	        this.rows.forEach(function (row) {
	            row.cells && row.cells.forEach(function (cell) {
	                cell.columnWidth = null;
	            });
	        });
	    };
	    TableDrawElement.prototype.removeRow = function (index) {
	        // debugger
	        var row = this.rows[index];
	        this.removeChild(row);
	        this.rows.splice(index, 1);
	        this.resetRowIndex();
	    };
	    Object.defineProperty(TableDrawElement.prototype, "key", {
	        get: function () {
	            return this.dataset;
	        },
	        set: function (value) {
	            this.dataset = value;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    Object.defineProperty(TableDrawElement.prototype, "height", {
	        get: function () {
	            var drawContext = this.getDrawObject().getDrawContext();
	            return Index_1.Unit.toMillimeter(drawContext.clientHeight);
	        },
	        set: function (value) {
	            var drawContext = this.getDrawObject().getDrawContext();
	            drawContext.style.height = Index_1.Unit.toPixel(value) + 'px';
	            if (this._height !== undefined && (value && typeof value == 'string')) {
	                Index_2.EventManager.broadcast('TABLE_RESIZE_EVENT', { event: { target: this } });
	            }
	            this._height = value;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    TableDrawElement.prototype.getDrawObject = function () {
	        return this.drawObject;
	    };
	    TableDrawElement.prototype.getMetadata = function (key) {
	        var metadata = {
	            borderWidth: {
	                defaultValue: 1,
	                value: this.borderWidth,
	                type: Number
	            },
	            cellBorderWidth: {
	                defaultValue: 1,
	                value: this.cellBorderWidth,
	                type: Number
	            },
	            borderStyle: {
	                defaultValue: undefined,
	                value: this.borderStyle,
	                type: Number
	            },
	            cellBorderStyle: {
	                defaultValue: undefined,
	                value: this.cellBorderStyle,
	                type: Number
	            },
	            width: {
	                value: this.width,
	                type: Number
	            },
	            key: {
	                defaultValue: null,
	                value: this.dataset,
	                type: String
	            }
	        };
	        return metadata[key];
	    };
	    TableDrawElement.prototype.getComputedStyle = function (key) {
	        var propertyMetadata = this.getMetadata(key);
	        if (propertyMetadata.defaultValue == propertyMetadata.value) {
	            return '';
	        }
	        return key + ":" + propertyMetadata.value + ";";
	    };
	    TableDrawElement.prototype.getComputedProperty = function (key) {
	        var propertyMetadata = this.getMetadata(key);
	        if (propertyMetadata.defaultValue == propertyMetadata.value) {
	            return '';
	        }
	        return key + "=\"" + propertyMetadata.value + "\" ";
	    };
	    TableDrawElement.prototype.getColumns = function () {
	        var children = this.children[0].children;
	        var columns = [];
	        for (var i = 0; i < children.length; i++) {
	            var txt = children[i];
	            var lineHeight = "";
	            if (txt.lineHeight) {
	                lineHeight = "lineHeight:" + txt.lineHeight + ";";
	            }
	            columns.push({
	                aliasName: txt.aliasName,
	                key: txt.text
	            });
	        }
	        return columns;
	    };
	    TableDrawElement.prototype.getScriptTemplate = function (styles) {
	        var scripts = [];
	        var columns = this.children[0].children;
	        var cells = [];
	        var titles = [];
	        var width = this.getComputedProperty('width');
	        var style = "";
	        if (styles.length) {
	            style = "style=\"" + styles.join('') + "\"";
	        }
	        scripts.push("\n<table " + style + " " + width + ">");
	        for (var i = 0; i < columns.length; i++) {
	            var element = columns[i].children[0];
	            var column = element.clone();
	            var dataItem = (column.forElement ? column.forElement : column);
	            var text = dataItem.text;
	            var width_1 = columns[i].width;
	            dataItem.left = element.left;
	            dataItem.top = element.top;
	            dataItem.text = dataItem.aliasName;
	            titles.push("<th width=\"" + width_1 + "\">" + column.getTemplate() + "</th>");
	            dataItem.aliasName = dataItem.aliasName + '数据';
	            dataItem.text = '<%=' + this.key + '[i].' + text + '%>';
	            cells.push("<td width=\"" + width_1 + "\">" + column.getTemplate() + "</td>");
	        }
	        if (this.thead === true || this.thead === "true") {
	            scripts.push('<tr>' + titles.join('') + '</tr>');
	        }
	        scripts.push('<%for(var i=0; i<' + this.key + '.length; i++){%>');
	        scripts.push('<tr>' + cells.join('') + '</tr>');
	        scripts.push('<%}%>\n</table>');
	        return scripts.join('\n');
	    };
	    TableDrawElement.prototype.getTemplate = function () {
	        var rows = [];
	        var borderWidth = this.getComputedStyle('borderWidth');
	        var borderStyle = this.getComputedStyle('borderStyle');
	        var cellBorderStyle = this.getComputedStyle('cellBorderStyle');
	        var cellBorderWidth = this.getComputedStyle('cellBorderWidth');
	        var key = this.getComputedProperty('key');
	        var width = this.getComputedProperty('width');
	        var styles = [borderWidth, borderStyle, cellBorderStyle, cellBorderWidth];
	        var style = "";
	        var thead = this.thead === false || this.thead === "false" ? ' editor:thead="' + this.thead + '"' : '';
	        if (this.component && this.dataset) {
	            return this.getScriptTemplate(styles);
	        }
	        for (var _i = 0, _a = this.children; _i < _a.length; _i++) {
	            var item = _a[_i];
	            var row = item;
	            rows.push(row.getTemplate());
	        }
	        var height = "";
	        if (this._height) {
	            height = "height=\"" + this._height + "\"";
	        }
	        if (styles.length) {
	            style = "style=\"" + styles.join('') + "\"";
	        }
	        return "\n         <table " + style + " " + (key ? "editor:" + key : "") + " " + width + " " + height + " " + thead + ">\n            " + rows.join('\n') + "\n         </table>\n         ";
	    };
	    TableDrawElement.prototype.showControlHandleForCell = function () {
	        this.control.showControlHandleForCell();
	    };
	    TableDrawElement.prototype.hideControlHandleForCell = function () {
	        this.control.hideControlHandleForCell();
	    };
	    TableDrawElement.prototype.resizeColumn = function (index, column) {
	        var rows = this.rows;
	        var width = column.width ? Number(column.width) : Number(column);
	        var control = column.control;
	        for (var _i = 0, rows_1 = rows; _i < rows_1.length; _i++) {
	            var row = rows_1[_i];
	            var cell = row.cells[index];
	            var offsetWidth = width - Number(cell.width);
	            var relative = row.cells[index + 1];
	            if (control == 'cell-lm' || !relative) {
	                relative = row.cells[index - 1];
	                cell.left = Number(column.left);
	            }
	            if (!relative) {
	                return;
	            }
	            cell.columnWidth = Number(width.toFixed(2));
	            relative.columnWidth = Number((Number(relative.width) - offsetWidth).toFixed(2));
	            relative.render();
	            cell.render();
	        }
	    };
	    TableDrawElement.prototype.resizeRow = function (index, data) {
	    };
	    TableDrawElement.prototype.render = function () {
	        var drawObject = this.getDrawObject();
	        var status = drawObject.status;
	        _super.prototype.render.call(this);
	        if (status == "rendered") {
	            this.rows.forEach(function (row) {
	                row.cells && row.cells.forEach(function (cell) {
	                    cell.columnWidth = null;
	                    cell.rowHeight = null;
	                });
	            });
	        }
	        this.clonePreRow();
	    };
	    //存储一份row，在表格打印项时，可以勾选展示列
	    TableDrawElement.prototype.clonePreRow = function () {
	        var me = this;
	        var preRow = [];
	        this.rows.map(function (item) {
	            preRow.push(item.clone());
	        });
	        this.preRow = preRow;
	        this.setPreColumns();
	    };
	    //表格之前的所有列
	    TableDrawElement.prototype.setPreColumns = function () {
	        if (this.preRow[0] && this.preRow[0].children) {
	            var children = this.preRow[0].children;
	            var columns = [];
	            for (var i = 0; i < children.length; i++) {
	                if (children[i].firstChild instanceof TextDrawElement_1.TextDrawElement) {
	                    var txt = children[i].firstChild;
	                    columns.push({
	                        aliasName: txt.aliasName,
	                        key: txt.text,
	                        visible: false
	                    });
	                }
	                else if (children[i].firstChild instanceof LayoutDrawElement_1.LayoutDrawElement) {
	                    if (children[i].firstChild.firstChild instanceof TextDrawElement_1.TextDrawElement) {
	                        var txt = children[i].firstChild.firstChild;
	                        columns.push({
	                            aliasName: txt.aliasName,
	                            key: txt.text,
	                            visible: false
	                        });
	                    }
	                }
	            }
	            this.preColumns = columns;
	        }
	    };
	    //调整列
	    TableDrawElement.prototype.onTableAdjustCol = function (args) {
	        var me = this;
	        // if(args.id != me.id){
	        //     return;
	        // }
	        if (!(args.draw == me.drawObject)) {
	            return;
	        }
	        //移除所有列
	        while (this.rows[0].cells.length) {
	            this.removeColumn(this.rows[0].cells.length - 1);
	        }
	        var checked = args.data;
	        me.preColumns.map(function (item) {
	            item.visible = false;
	        });
	        checked.map(function (item) {
	            me.preColumns[item].visible = true;
	            //添加展示列
	            for (var i = 0; i < me.rows.length; i++) {
	                me.rows[i].addCell(me.preRow[i].cells[item].clone());
	                me.rows[i].cells[me.rows[i].cells.length - 1].children.map(function (vo) {
	                    vo.update([
	                        {
	                            propertyName: 'left',
	                            propertyValue: 0.2
	                        },
	                        {
	                            propertyName: 'top',
	                            propertyValue: 0.2
	                        }
	                    ]);
	                    if (vo instanceof LayoutDrawElement_1.LayoutDrawElement) {
	                        var childEle = vo.firstChild;
	                        childEle.update([
	                            {
	                                propertyName: 'left',
	                                propertyValue: 0.2
	                            },
	                            {
	                                propertyName: 'top',
	                                propertyValue: 0.2
	                            }
	                        ]);
	                    }
	                });
	            }
	        });
	    };
	    TableDrawElement.prototype.clone = function () {
	        var offset = 10;
	        var cloned = new TableDrawElement();
	        var rows = this.rows;
	        cloned.left = Number(this.left) + offset;
	        cloned.top = Number(this.top) + offset;
	        cloned.width = this.width;
	        // cloned.drawObject = new DOMTableDraw();
	        // cloned.control = new TableControlHandle(cloned);
	        for (var i = 0; i < rows.length; i++) {
	            cloned.addRow(rows[i].clone());
	        }
	        return cloned;
	    };
	    return TableDrawElement;
	}(DrawElement_1.DrawElement));
	exports.TableDrawElement = TableDrawElement;


/***/ }),
/* 30 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var Dispatcher_1 = __webpack_require__(4);
	var Index_1 = __webpack_require__(16);
	/**
	 * 状态对象
	 */
	var ControlHandleSataus = (function () {
	    function ControlHandleSataus() {
	    }
	    ControlHandleSataus.prototype.hide = function (controlHandle) {
	        Index_1.DOMUtils.ApplyStyle(controlHandle.renderNode, {
	            display: "none"
	        });
	        controlHandle.status = new HiddendSataus();
	    };
	    ControlHandleSataus.prototype.show = function (controlHandle) {
	        Index_1.DOMUtils.ApplyStyle(controlHandle.renderNode, {
	            display: ""
	        });
	        controlHandle.status = new RenderedSataus();
	    };
	    return ControlHandleSataus;
	}());
	/**
	 * 初始状态
	 */
	var InitSataus = (function (_super) {
	    __extends(InitSataus, _super);
	    function InitSataus() {
	        return _super !== null && _super.apply(this, arguments) || this;
	    }
	    InitSataus.prototype.select = function (controlHandle) {
	        controlHandle.render();
	        controlHandle.status = new RenderedSataus();
	        controlHandle.select();
	    };
	    InitSataus.prototype.draw = function (controlHandle) {
	        var div = controlHandle.renderNode;
	        div.className = 'contorl-handle';
	        Index_1.DOMUtils.ApplyStyle(div, {
	            border: "1px dotted #000",
	            position: 'absolute'
	        });
	        div.innerHTML = "\n            <span data-pointer=\"lt\" class=\"contorl contorl-lt\"></span>\n            <span data-pointer=\"lm\" class=\"contorl contorl-lm\"></span>\n            <span data-pointer=\"lb\" class=\"contorl contorl-lb\"></span>\n            <span data-pointer=\"mt\" class=\"contorl contorl-mt\"></span>\n            <span data-pointer=\"mb\" class=\"contorl contorl-mb\"></span>\n            <span data-pointer=\"rt\" class=\"contorl contorl-rt\"></span>\n            <span data-pointer=\"rm\" class=\"contorl contorl-rm\"></span>\n            <span data-pointer=\"rb\" class=\"contorl contorl-rb\"></span>\n        ";
	    };
	    return InitSataus;
	}(ControlHandleSataus));
	exports.InitSataus = InitSataus;
	/**
	 * 选中状态
	 */
	var SelectedStatus = (function (_super) {
	    __extends(SelectedStatus, _super);
	    function SelectedStatus() {
	        return _super !== null && _super.apply(this, arguments) || this;
	    }
	    SelectedStatus.prototype.select = function (controlHandle) {
	        return;
	    };
	    SelectedStatus.prototype.draw = function (controlHandle) {
	        var renderNode = controlHandle.getDrawContext();
	        var _a = controlHandle.contextElement, left = _a.left, top = _a.top, width = _a.width, height = _a.height, zIndex = _a.zIndex, rotation = _a.rotation;
	        var element = controlHandle.contextElement.getDrawObject().getDrawContext();
	        var clientWidth = element.clientWidth;
	        var clientHeight = element.clientHeight;
	        Index_1.DOMUtils.ApplyStyle(renderNode, {
	            left: Index_1.Unit.toPixel(left) + 'px',
	            top: Index_1.Unit.toPixel(top) + 'px',
	            height: (height === null) ? clientHeight + "px" : Index_1.Unit.toPixel(height) + 'px',
	            width: (width === null) ? clientWidth + "px" : Index_1.Unit.toPixel(width) + 'px',
	            display: "",
	            zIndex: zIndex,
	            transform: 'rotate(' + rotation + 'deg)',
	            msTransform: 'rotate(' + rotation + 'deg)',
	            webkitTransform: 'rotate(' + rotation + 'deg)'
	        });
	        controlHandle.status = new RenderedSataus();
	    };
	    return SelectedStatus;
	}(ControlHandleSataus));
	/**
	 *  渲染完成状态
	 */
	var RenderedSataus = (function (_super) {
	    __extends(RenderedSataus, _super);
	    function RenderedSataus() {
	        return _super !== null && _super.apply(this, arguments) || this;
	    }
	    RenderedSataus.prototype.select = function (controlHandle) {
	        this.draw(controlHandle);
	        controlHandle.status = new SelectedStatus();
	    };
	    RenderedSataus.prototype.draw = function (controlHandle) {
	        var renderNode = controlHandle.getDrawContext();
	        var _a = controlHandle.contextElement, left = _a.left, top = _a.top, width = _a.width, height = _a.height, zIndex = _a.zIndex, rotation = _a.rotation;
	        var element = controlHandle.contextElement.getDrawObject().getDrawContext();
	        var clientWidth = element.clientWidth;
	        var clientHeight = element.clientHeight;
	        Index_1.DOMUtils.ApplyStyle(renderNode, {
	            zIndex: zIndex,
	            left: Index_1.Unit.toPixel(left) + 'px',
	            top: Index_1.Unit.toPixel(top) + 'px',
	            height: (height === null) ? clientHeight + "px" : Index_1.Unit.toPixel(height) + 'px',
	            width: (width === null) ? clientWidth + "px" : Index_1.Unit.toPixel(width) + 'px',
	            display: "",
	            transform: 'rotate(' + rotation + 'deg)',
	            msTransform: 'rotate(' + rotation + 'deg)',
	            webkitTransform: 'rotate(' + rotation + 'deg)'
	        });
	    };
	    return RenderedSataus;
	}(ControlHandleSataus));
	/**
	 *  已移除状态
	 */
	var RemovedSataus = (function (_super) {
	    __extends(RemovedSataus, _super);
	    function RemovedSataus() {
	        return _super !== null && _super.apply(this, arguments) || this;
	    }
	    RemovedSataus.prototype.select = function (controlHandle) { return; };
	    RemovedSataus.prototype.draw = function (controlHandle) {
	        controlHandle.render();
	        controlHandle.status = new RenderedSataus();
	    };
	    RemovedSataus.prototype.hide = function () { return; };
	    return RemovedSataus;
	}(ControlHandleSataus));
	/**
	 *  已移除状态
	 */
	var HiddendSataus = (function (_super) {
	    __extends(HiddendSataus, _super);
	    function HiddendSataus() {
	        return _super !== null && _super.apply(this, arguments) || this;
	    }
	    //隐藏状态不可以选中
	    HiddendSataus.prototype.select = function (controlHandle) {
	        controlHandle.status = new SelectedStatus();
	        controlHandle.status.draw(controlHandle);
	    };
	    HiddendSataus.prototype.draw = function (controlHandle) {
	        // DOMUtils.ApplyStyle(controlHandle.renderNode, {
	        //     display:""
	        // });
	        // controlHandle.status = new RenderedSataus();
	        // controlHandle.status.draw(controlHandle);
	        var renderNode = controlHandle.getDrawContext();
	        var _a = controlHandle.contextElement, left = _a.left, top = _a.top, width = _a.width, height = _a.height, zIndex = _a.zIndex, rotation = _a.rotation;
	        var element = controlHandle.contextElement.getDrawObject().getDrawContext();
	        var clientWidth = element.clientWidth;
	        var clientHeight = element.clientHeight;
	        Index_1.DOMUtils.ApplyStyle(renderNode, {
	            zIndex: zIndex,
	            left: Index_1.Unit.toPixel(left) + 'px',
	            top: Index_1.Unit.toPixel(top) + 'px',
	            height: (height === null) ? clientHeight + "px" : Index_1.Unit.toPixel(height) + 'px',
	            width: (width === null) ? clientWidth + "px" : Index_1.Unit.toPixel(width) + 'px',
	            transform: 'rotate(' + rotation + 'deg)',
	            msTransform: 'rotate(' + rotation + 'deg)',
	            webkitTransform: 'rotate(' + rotation + 'deg)'
	        });
	    };
	    return HiddendSataus;
	}(ControlHandleSataus));
	/**
	 *
	/**
	 * DOMTextControlHandle
	 */
	var DOMControlHandle = (function () {
	    function DOMControlHandle(contextElement) {
	        this.contextElement = contextElement;
	        this.renderNode = document.createElement('div');
	        this.onPropertychange = this.onPropertychange.bind(this);
	        this.onSelectChange = this.onSelectChange.bind(this);
	        this.status = new InitSataus();
	        this.status.draw(this);
	        Dispatcher_1.EventManager.register('PROPERTY_CHANGE_EVENT', this.onPropertychange);
	        Dispatcher_1.EventManager.register('DESINGER_SELECTED_EVENT', this.onSelectChange);
	    }
	    DOMControlHandle.prototype.render = function () {
	        var drawElement = this.contextElement;
	        var parent = drawElement.parent;
	        var context = parent.getDrawObject().getDrawContext();
	        var renderNode = this.getDrawContext();
	        context.appendChild(renderNode);
	    };
	    DOMControlHandle.prototype.handle = function (ev, elements) {
	        var pointer = ev.target.getAttribute('data-pointer');
	        if (pointer) {
	            Dispatcher_1.Dispatcher.dispatch({
	                type: 'RIZE_DRAG_COMMAND',
	                data: {
	                    elements: elements,
	                    pointer: pointer
	                }
	            });
	            return;
	        }
	        Dispatcher_1.Dispatcher.dispatch({
	            type: 'MOVE_DRAG_COMMAND',
	            data: {
	                elements: elements
	            }
	        });
	    };
	    DOMControlHandle.prototype.select = function () {
	        this.status.select(this);
	    };
	    DOMControlHandle.prototype.onSelectChange = function (args) {
	        var self = this;
	        if (args.element == this.contextElement || args.element.forElement == this.contextElement) {
	            this.status.select(this);
	        }
	    };
	    DOMControlHandle.prototype.onPropertychange = function (args) {
	        var target = args.event.target;
	        var ret = target.control == this;
	        if (ret) {
	            this.draw(target);
	        }
	    };
	    DOMControlHandle.prototype.getDrawContext = function () {
	        return this.renderNode;
	    };
	    DOMControlHandle.prototype.isActive = function (args) {
	        var target = args.event.target;
	        return target == this.renderNode || target.parentNode == this.renderNode;
	    };
	    DOMControlHandle.prototype.remove = function () {
	        this.status = new RemovedSataus();
	        if (this.renderNode && this.renderNode.parentNode) {
	            this.renderNode.parentNode.removeChild(this.renderNode);
	        }
	    };
	    DOMControlHandle.prototype.draw = function (drawElement) {
	        this.contextElement = drawElement;
	        this.status.draw(this);
	    };
	    DOMControlHandle.prototype.show = function () {
	        this.status.draw(this);
	    };
	    DOMControlHandle.prototype.hide = function () {
	        this.status.hide(this);
	    };
	    return DOMControlHandle;
	}());
	exports.DOMControlHandle = DOMControlHandle;


/***/ }),
/* 31 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var DrawElement_1 = __webpack_require__(23);
	var Index_1 = __webpack_require__(16);
	/**
	 * 表格行
	 */
	var DOMTableRowDraw = (function () {
	    function DOMTableRowDraw() {
	        this.renderNode = Index_1.DOMUtils.CreateElement('tr', { className: 'element-table-row' });
	    }
	    DOMTableRowDraw.prototype.getDrawContext = function () {
	        return this.renderNode;
	    };
	    DOMTableRowDraw.prototype.isActive = function () {
	        return true;
	    };
	    DOMTableRowDraw.prototype.remove = function () {
	        this.status = "removed";
	        if (this.renderNode && this.renderNode.parentNode) {
	            this.renderNode.parentNode.removeChild(this.renderNode);
	        }
	    };
	    DOMTableRowDraw.prototype.draw = function (row) {
	        var parent = row.parent;
	        var renderContext = parent && parent.getDrawObject().getDrawContext();
	        if (renderContext.rows[row.index]) {
	            renderContext.insertBefore(this.renderNode, renderContext.rows[row.index]);
	        }
	        else {
	            renderContext.appendChild(this.renderNode);
	        }
	    };
	    return DOMTableRowDraw;
	}());
	var TableRowDrawElement = (function (_super) {
	    __extends(TableRowDrawElement, _super);
	    function TableRowDrawElement() {
	        var _this = _super.call(this) || this;
	        _this.cells = [];
	        _this.drawObject = new DOMTableRowDraw();
	        return _this;
	    }
	    TableRowDrawElement.prototype.resize = function (value) {
	        var offset = Number(value.height) - Number(this.cells[0].height);
	        var table = this.table;
	        var relative = table.rows[this.index + 1];
	        if (value.pointer == "cell-mt") {
	            relative = table.rows[this.index - 1];
	        }
	        if (relative) {
	            relative.cells.forEach(function (cell) {
	                cell.rowHeight = parseFloat((Number(cell.height) - offset).toFixed(2));
	                cell.update();
	            });
	        }
	        this.cells.forEach(function (cell) {
	            cell.rowHeight = value.height;
	            cell.top = Number(value.top);
	            cell.update();
	        });
	        if (value.pointer == "cell-mb" && this.index == table.rows.length - 1) {
	            table.height = Number((Number(table.height) + offset).toFixed(2));
	        }
	        if (value.pointer == "cell-mt" && this.index == 0) {
	            table.height = Number((Number(table.height) + offset).toFixed(2));
	            table.top = Number(Number(table.top) - offset).toFixed(2);
	            table.update();
	        }
	    };
	    TableRowDrawElement.prototype.addCell = function (cell, index) {
	        var col = index === undefined ? this.cells.length : index;
	        cell.row = this;
	        cell.index = [this.index, col];
	        this.addChild(cell, index);
	        if (index !== undefined) {
	            this.cells.splice(index, 0, cell);
	        }
	        else {
	            this.cells.push(cell);
	        }
	        //retset column index
	        this.resetColumnIndex();
	    };
	    TableRowDrawElement.prototype.resetColumnIndex = function () {
	        for (var i = 0; i < this.cells.length; i++) {
	            this.cells[i].index[1] = i;
	        }
	    };
	    TableRowDrawElement.prototype.removeCell = function (index) {
	        var element = this.cells[index];
	        this.removeChild(element);
	        this.cells.splice(index, 1);
	        this.resetColumnIndex();
	        this.cells.forEach(function (cell) {
	            cell.columnWidth = null;
	        });
	    };
	    TableRowDrawElement.prototype.getDrawObject = function () {
	        return this.drawObject;
	    };
	    TableRowDrawElement.prototype.getTemplate = function () {
	        var cells = [];
	        for (var i = 0; i < this.cells.length; i++) {
	            cells.push(this.cells[i].getTemplate());
	        }
	        return '<tr>\n\t\t' + cells.join('\n\t\t') + '\n\t\t\t</tr>';
	    };
	    //不可选中表格行
	    TableRowDrawElement.prototype.isActive = function () {
	        return false;
	    };
	    TableRowDrawElement.prototype.clone = function () {
	        var cloned = new TableRowDrawElement();
	        var cells = this.cells;
	        for (var i = 0; i < cells.length; i++) {
	            cloned.addCell(cells[i].clone());
	        }
	        return cloned;
	    };
	    return TableRowDrawElement;
	}(DrawElement_1.DrawElement));
	exports.TableRowDrawElement = TableRowDrawElement;


/***/ }),
/* 32 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var DrawElement_1 = __webpack_require__(23);
	var Index_1 = __webpack_require__(16);
	var ControlHandle_1 = __webpack_require__(30);
	var Index_2 = __webpack_require__(2);
	var TableCellInitSataus = (function (_super) {
	    __extends(TableCellInitSataus, _super);
	    function TableCellInitSataus() {
	        return _super !== null && _super.apply(this, arguments) || this;
	    }
	    TableCellInitSataus.prototype.draw = function (controlHandle) {
	        var div = controlHandle.renderNode;
	        div.className = 'contorl-handle';
	        Index_1.DOMUtils.ApplyStyle(div, {
	            border: "1px dotted #000",
	            position: 'absolute'
	        });
	        div.innerHTML = "\n            <span data-pointer=\"cell-lm\" class=\"contorl contorl-lm\"></span>\n            <span data-pointer=\"cell-mt\" class=\"contorl contorl-mt\"></span>\n            <span data-pointer=\"cell-mb\" class=\"contorl contorl-mb\"></span>\n            <span data-pointer=\"cell-rm\" class=\"contorl contorl-rm\"></span>\n        ";
	    };
	    return TableCellInitSataus;
	}(ControlHandle_1.InitSataus));
	var TableCellControl = (function (_super) {
	    __extends(TableCellControl, _super);
	    function TableCellControl(contextElement) {
	        var _this = _super.call(this, contextElement) || this;
	        _this.status = new TableCellInitSataus();
	        _this.status.draw(_this);
	        return _this;
	    }
	    TableCellControl.prototype.hide = function () {
	        var drawElement = this.contextElement;
	        _super.prototype.hide.call(this);
	        drawElement.row.table.hideControlHandleForCell();
	    };
	    TableCellControl.prototype.show = function () {
	        var drawElement = this.contextElement;
	        _super.prototype.show.call(this);
	        drawElement.row.table.showControlHandleForCell();
	    };
	    TableCellControl.prototype.onPropertychange = function () {
	        return;
	    };
	    TableCellControl.prototype.render = function () {
	        var drawElement = this.contextElement;
	        var context = drawElement.row.table.parent.getDrawContext();
	        // var context = drawElement.getDrawObject().getDrawContext() as HTMLElement;
	        var renderNode = this.getDrawContext();
	        context.appendChild(renderNode);
	    };
	    return TableCellControl;
	}(ControlHandle_1.DOMControlHandle));
	exports.TableCellControl = TableCellControl;
	var TableCellDrawStatus = (function (_super) {
	    __extends(TableCellDrawStatus, _super);
	    function TableCellDrawStatus() {
	        return _super !== null && _super.apply(this, arguments) || this;
	    }
	    TableCellDrawStatus.prototype.select = function (controlHandle) { return; };
	    TableCellDrawStatus.prototype.draw = function (cell) {
	        var renderNode = cell.getDrawObject().getDrawContext();
	        var height = Index_1.Unit.toPixel(cell.height) + "px";
	        var width = Index_1.Unit.toPixel(cell.width) + "px";
	        // if(cell.columnWidth==null){
	        //     width = "auto";
	        // }
	        renderNode.setAttribute('colspan', cell.colspan.toString());
	        renderNode.setAttribute('rowspan', cell.rowspan.toString());
	        Index_1.DOMUtils.ApplyStyle(renderNode, {
	            height: height,
	            width: width
	        });
	    };
	    TableCellDrawStatus.prototype.hide = function (cell) {
	        var drawObject = cell.getDrawObject();
	        var renderNode = cell.getDrawObject().getDrawContext();
	        Index_1.DOMUtils.ApplyStyle(renderNode, {
	            display: "none"
	        });
	        drawObject.status = new HiddendSataus();
	    };
	    TableCellDrawStatus.prototype.show = function (cell) {
	        var renderNode = cell.getDrawObject().getDrawContext();
	        var drawObject = cell.getDrawObject();
	        Index_1.DOMUtils.ApplyStyle(renderNode, {
	            display: ""
	        });
	        drawObject.status = new HiddendSataus();
	    };
	    return TableCellDrawStatus;
	}(DrawElement_1.DrawStatus));
	var InitDrawStatus = (function (_super) {
	    __extends(InitDrawStatus, _super);
	    function InitDrawStatus() {
	        return _super !== null && _super.apply(this, arguments) || this;
	    }
	    InitDrawStatus.prototype.select = function (controlHandle) { return; };
	    InitDrawStatus.prototype.draw = function (cell) {
	        var drawObject = cell.getDrawObject();
	        var parent = cell.parent;
	        var renderContext = parent && parent.getDrawObject().getDrawContext();
	        var renderNode = cell.getDrawObject().getDrawContext();
	        _super.prototype.draw.call(this, cell);
	        if (renderContext.cells[cell.index[1]]) {
	            renderContext.insertBefore(renderNode, renderContext.cells[cell.index[1]]);
	        }
	        else {
	            renderContext.appendChild(renderNode);
	        }
	        drawObject.status = new RenderedDrawStatus();
	    };
	    return InitDrawStatus;
	}(TableCellDrawStatus));
	var SelectedDrawStatus = (function (_super) {
	    __extends(SelectedDrawStatus, _super);
	    function SelectedDrawStatus() {
	        return _super !== null && _super.apply(this, arguments) || this;
	    }
	    SelectedDrawStatus.prototype.select = function (cell) { return; };
	    SelectedDrawStatus.prototype.draw = function (cell) {
	        var drawObject = cell.getDrawObject();
	        _super.prototype.draw.call(this, cell);
	        drawObject.status = new RenderedDrawStatus();
	    };
	    return SelectedDrawStatus;
	}(TableCellDrawStatus));
	var HiddendSataus = (function (_super) {
	    __extends(HiddendSataus, _super);
	    function HiddendSataus() {
	        return _super !== null && _super.apply(this, arguments) || this;
	    }
	    HiddendSataus.prototype.select = function (cell) { return; };
	    HiddendSataus.prototype.draw = function (cell) { return; };
	    return HiddendSataus;
	}(TableCellDrawStatus));
	var RenderedDrawStatus = (function (_super) {
	    __extends(RenderedDrawStatus, _super);
	    function RenderedDrawStatus() {
	        return _super !== null && _super.apply(this, arguments) || this;
	    }
	    RenderedDrawStatus.prototype.select = function (cell) {
	        var drawObject = cell.getDrawObject();
	        this.draw(cell);
	        drawObject.status = new SelectedDrawStatus();
	    };
	    return RenderedDrawStatus;
	}(TableCellDrawStatus));
	/**
	 * 表格单元格
	 */
	var DOMTableCellDraw = (function () {
	    function DOMTableCellDraw() {
	        this.status = new InitDrawStatus();
	        this.isAutoHeight = true;
	        this.isAutoWidth = true;
	        this.isFirstDraw = true;
	        this.renderNode = Index_1.DOMUtils.CreateElement('td', { className: 'element-table-cell' });
	        this.renderNode.innerHTML = ' ';
	    }
	    Object.defineProperty(DOMTableCellDraw.prototype, "width", {
	        get: function () {
	            var node = this.renderNode;
	            return node.offsetWidth;
	        },
	        set: function (value) {
	            var node = this.renderNode;
	            // console.log('width ' + value, ", " + Unit.toPixel(value))
	            node.style.width = Index_1.Unit.toPixel(value) + 'px';
	            this.isAutoWidth = false;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    Object.defineProperty(DOMTableCellDraw.prototype, "height", {
	        get: function () {
	            var node = this.renderNode;
	            return node.offsetHeight || DOMTableCellDraw.MIN_HEIGHT;
	        },
	        set: function (value) {
	            var node = this.renderNode;
	            node.style.height = Index_1.Unit.toPixel(value) + 'px';
	            this.isAutoHeight = false;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    Object.defineProperty(DOMTableCellDraw.prototype, "left", {
	        get: function () {
	            var node = this.renderNode;
	            var table = node.offsetParent;
	            return node.offsetParent ? node.offsetLeft + table.offsetLeft : node.offsetLeft;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    Object.defineProperty(DOMTableCellDraw.prototype, "top", {
	        get: function () {
	            var node = this.renderNode;
	            var table = node.offsetParent;
	            return node.offsetParent ? node.offsetTop + table.offsetTop : node.offsetTop;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    DOMTableCellDraw.prototype.getDrawContext = function () {
	        return this.renderNode;
	    };
	    DOMTableCellDraw.prototype.isActive = function (args) {
	        var ret = args.event.target == this.renderNode || this.renderNode.contains(args.event.target);
	        return ret;
	    };
	    DOMTableCellDraw.prototype.remove = function () {
	        this.status = new InitDrawStatus();
	        if (this.renderNode && this.renderNode.parentNode) {
	            this.renderNode.parentNode.removeChild(this.renderNode);
	        }
	    };
	    DOMTableCellDraw.prototype.draw = function (cell) {
	        if (!this.isFirstDraw) {
	            this.isAutoHeight = false;
	        }
	        else {
	            this.isFirstDraw = false;
	        }
	        this.status.draw(cell);
	    };
	    return DOMTableCellDraw;
	}());
	DOMTableCellDraw.MIN_HEIGHT = 19.9;
	/**
	 * 表格绘制元素
	 */
	var TableCellDrawElement = (function (_super) {
	    __extends(TableCellDrawElement, _super);
	    function TableCellDrawElement(isThead) {
	        var _this = _super.call(this) || this;
	        _this.index = [];
	        // public left = 0;
	        // public top = 0;
	        _this.colspan = 1;
	        _this.rowspan = 1;
	        _this.isThead = isThead ? isThead : false;
	        _this.drawObject = new DOMTableCellDraw();
	        _this.control = new TableCellControl(_this);
	        _this.onTableResize = _this.onTableResize.bind(_this);
	        Index_2.EventManager.register('TABLE_RESIZE_EVENT', _this.onTableResize);
	        return _this;
	    }
	    Object.defineProperty(TableCellDrawElement.prototype, "left", {
	        get: function () { return Index_1.Unit.toMillimeter(this.drawObject.left); },
	        set: function (value) { ; },
	        enumerable: true,
	        configurable: true
	    });
	    Object.defineProperty(TableCellDrawElement.prototype, "top", {
	        get: function () { return Index_1.Unit.toMillimeter(this.drawObject.top); },
	        set: function (value) { ; },
	        enumerable: true,
	        configurable: true
	    });
	    Object.defineProperty(TableCellDrawElement.prototype, "width", {
	        get: function () {
	            return this.columnWidth || Index_1.Unit.toMillimeter(this.drawObject.width);
	        },
	        set: function (value) {
	            this.columnWidth = value;
	            this.drawObject.width = value;
	            this.drawObject.isAutoWidth = false;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    Object.defineProperty(TableCellDrawElement.prototype, "column", {
	        get: function () {
	            return this.width;
	        },
	        set: function (value) {
	            var index = this.index[1];
	            this.row.table.resizeColumn(index, value);
	            Index_2.EventManager.broadcast('PROPERTY_CHANGE_EVENT', { event: { target: this } });
	        },
	        enumerable: true,
	        configurable: true
	    });
	    TableCellDrawElement.prototype.onTableResize = function (args) {
	        if (this.row.table && this.row.table.id == args.event.target.id) {
	            var self = this;
	            //暂时注释调
	            setTimeout(function () {
	                self.render();
	            }, 100);
	        }
	    };
	    Object.defineProperty(TableCellDrawElement.prototype, "computedHeight", {
	        get: function () {
	            return this.height;
	        },
	        set: function (value) {
	            this.row && this.row.resize({
	                height: value,
	                pointer: "cell-mb"
	            });
	        },
	        enumerable: true,
	        configurable: true
	    });
	    Object.defineProperty(TableCellDrawElement.prototype, "height", {
	        get: function () {
	            return this.rowHeight ? this.rowHeight : Index_1.Unit.toMillimeter(this.drawObject.height);
	        },
	        set: function (value) {
	            this.rowHeight = value;
	            this.drawObject.height = value;
	            this.drawObject.isAutoHeight = false;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    TableCellDrawElement.prototype.getDrawObject = function () {
	        return this.drawObject;
	    };
	    TableCellDrawElement.prototype.getMetadata = function (key) {
	        var metadata = {
	            rowspan: {
	                defaultValue: 1,
	                value: this.rowspan,
	                type: Number
	            },
	            colspan: {
	                defaultValue: 1,
	                value: this.colspan,
	                type: Number
	            },
	            width: {
	                defaultValue: undefined,
	                value: this.width,
	                type: Number
	            },
	            height: {
	                defaultValue: undefined,
	                value: this.height,
	                type: Number
	            }
	        };
	        return metadata[key];
	    };
	    TableCellDrawElement.prototype.getComputedProperty = function (key) {
	        var propertyMetadata = this.getMetadata(key);
	        if (propertyMetadata.defaultValue == propertyMetadata.value) {
	            return '';
	        }
	        return key + "=\"" + propertyMetadata.value + "\" ";
	    };
	    TableCellDrawElement.prototype.getTemplate = function () {
	        var rowspan = this.getComputedProperty("rowspan");
	        var colspan = this.getComputedProperty("colspan");
	        var width = this.getComputedProperty("width");
	        var height = this.getComputedProperty("height");
	        var children = [];
	        for (var i = 0; i < this.children.length; i++) {
	            var child = this.children[i];
	            children.push(child.getTemplate());
	        }
	        if (this.row.index != 0 && this.drawObject.isAutoWidth) {
	            width = "";
	        }
	        /**
	         * 新建时，没有高度；表格高调整后，才会有高度
	         */
	        if (this.drawObject.isAutoHeight) {
	            height = "";
	        }
	        if (this.isThead) {
	            return '\t<th ' + colspan + rowspan + width + height + '>\n\t\t\t' + children.join('') + '\n\t\t\t</th>';
	        }
	        else {
	            return '\t<td ' + colspan + rowspan + width + height + '>\n\t\t\t' + children.join('') + '\n\t\t\t</td>';
	        }
	    };
	    TableCellDrawElement.prototype.clone = function () {
	        var cloned = new TableCellDrawElement();
	        var children = this.children;
	        for (var i = 0; i < children.length; i++) {
	            var elemnt = children[i];
	            cloned.addChild(elemnt.clone());
	        }
	        return cloned;
	    };
	    return TableCellDrawElement;
	}(DrawElement_1.DrawElement));
	exports.TableCellDrawElement = TableCellDrawElement;


/***/ }),
/* 33 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var DrawElement_1 = __webpack_require__(23);
	var EventManager_1 = __webpack_require__(5);
	var Index_1 = __webpack_require__(16);
	var ControlHandle_1 = __webpack_require__(30);
	/**
	 * 1 选中
	 * 2 拖动{ 1 onmousedown 2 isActive, 3 onmousemove, 修改选中元素left,top坐标，4 onmouseup }
	 * 3 生成模版代码
	 */
	/**
	 * 使用CSS DOM 绘制文本元素
	 */
	var DOMTextDraw = (function (_super) {
	    __extends(DOMTextDraw, _super);
	    function DOMTextDraw() {
	        var _this = _super.call(this) || this;
	        _this.status = "";
	        var renderNode = Index_1.DOMUtils.CreateElement('div');
	        var contentNode = Index_1.DOMUtils.CreateElement('div', { className: 'element-text-content' });
	        renderNode.appendChild(contentNode);
	        _this.contentNode = contentNode;
	        _this.renderNode = renderNode;
	        return _this;
	    }
	    DOMTextDraw.prototype.getEditContent = function () {
	        var contentNode = this.contentNode;
	        var text = contentNode.innerHTML || "";
	        return text.replace(/&nbsp;/g, ' ').
	            replace(/<br>/g, '\r\n').
	            replace(/<br \/>/g, '\r\n').
	            replace(/&lt;/g, '<').
	            replace(/&gt;/g, '>').
	            replace(/&amp;/g, '&').
	            replace(/<div>([\s\S\n]*?)<\/div>/g, '\r\n$1');
	    };
	    DOMTextDraw.prototype.getTextValue = function (text) {
	        return text.replace(/&/g, '&amp;').
	            replace(/ /g, '&nbsp;').
	            replace(/\r\n/g, '<br />').
	            replace(/\n/g, '<br />').
	            replace(/\\r\\n/g, "<br />");
	    };
	    DOMTextDraw.prototype.isActive = function (args) {
	        var target = args.event.target;
	        var ret = target == this.renderNode || this.renderNode.contains(target);
	        return ret;
	    };
	    DOMTextDraw.prototype.getOrientation = function (textElement) {
	        if (textElement.orientation == 'vertical') {
	            return 'tb-rl';
	        }
	        else {
	            return '';
	        }
	    };
	    DOMTextDraw.prototype.getFontUnderline = function (textElement) {
	        if (textElement.fontUnderLine) {
	            return 'underline';
	        }
	        else {
	            return 'none';
	        }
	    };
	    DOMTextDraw.prototype.getFontItalic = function (textElement) {
	        return textElement.fontItalic ? 'italic' : 'normal';
	    };
	    DOMTextDraw.prototype.edit = function (textElement) {
	        var element = this.contentNode;
	        var text = this.getTextValue(textElement.text);
	        if (text == textElement.defaultText) {
	            text = "";
	        }
	        element.innerHTML = text;
	        element.contentEditable = "true";
	        element.focus();
	    };
	    DOMTextDraw.prototype.blur = function () {
	        this.contentNode.contentEditable = "false";
	    };
	    DOMTextDraw.prototype.draw = function (textElement) {
	        var parent = textElement.parent;
	        var div = this.renderNode;
	        var rotation = textElement.rotation, left = textElement.left, top = textElement.top, opacity = textElement.opacity, backgroundColor = textElement.backgroundColor, fontColor = textElement.fontColor, width = textElement.width, height = textElement.height, fontSize = textElement.fontSize, fontFamily = textElement.fontFamily, letterSpacing = textElement.letterSpacing, lineHeight = textElement.lineHeight, fontWeight = textElement.fontWeight, align = textElement.align, valign = textElement.valign, zIndex = textElement.zIndex;
	        div = div;
	        if (!parent) {
	            return;
	        }
	        div.className = 'element-text';
	        if (Number(lineHeight)) {
	            lineHeight = lineHeight + 'mm';
	        }
	        var flex = {
	            "middle": "center",
	            "top": "flex-start",
	            "bottom": "flex-end"
	        };
	        var justify = {
	            "center": "center",
	            "left": "flex-start",
	            "right": "flex-end"
	        };
	        Index_1.DOMUtils.ApplyStyle(div, {
	            zIndex: zIndex,
	            opacity: opacity,
	            left: Index_1.Unit.toPixel(left) + 'px',
	            top: Index_1.Unit.toPixel(top) + 'px',
	            height: (height === null) ? "92%" : Index_1.Unit.toPixel(height) + 'px',
	            width: (width === null) ? "92%" : Index_1.Unit.toPixel(width) + 'px',
	            position: 'absolute',
	            fontSize: fontSize + "pt",
	            fontFamily: fontFamily,
	            letterSpacing: letterSpacing + 'pt',
	            lineHeight: lineHeight,
	            fontWeight: fontWeight,
	            transform: 'rotate(' + rotation + 'deg)',
	            msTransform: 'rotate(' + rotation + 'deg)',
	            webkitTransform: 'rotate(' + rotation + 'deg)',
	            //textAlign:align,
	            justifyContent: justify[align],
	            alignItems: flex[valign],
	            fontStyle: this.getFontItalic(textElement),
	            textDecoration: this.getFontUnderline(textElement),
	            writingMode: this.getOrientation(textElement),
	            backgroundColor: backgroundColor,
	            color: fontColor
	        });
	        var text = textElement.text;
	        if ((textElement.aliasName != textElement.defaultAliasName) && (textElement.aliasName != "") && (textElement.aliasName != "请输入内容")) {
	            text = textElement.aliasName;
	        }
	        this.contentNode.innerHTML = this.getTextValue(text);
	        this.renderNode = div;
	        if (this.status == "" || this.status == "removed") {
	            this.status = 'render';
	            var context_1 = parent.getDrawObject().getDrawContext();
	            context_1.appendChild(this.renderNode);
	        }
	    };
	    return DOMTextDraw;
	}(DrawElement_1.DrawObject));
	/**
	 * 文本元素
	 */
	var TextDrawElement = (function (_super) {
	    __extends(TextDrawElement, _super);
	    function TextDrawElement(orientation, width, height) {
	        var _this = _super.call(this) || this;
	        _this.text = designer_lang.textDefalutValue;
	        _this.width = 25;
	        _this.height = 5;
	        _this.left = 10;
	        _this.top = 10;
	        _this.fontSize = 8;
	        _this.fontFamily = "SimHei";
	        _this.letterSpacing = 0;
	        _this.scale = 1;
	        _this.fontColor = '#000';
	        _this.backgroundColor = '#fff';
	        _this.lineHeight = "";
	        _this.fontWeight = "normal"; //枚举值light(淡)、normal（正常）、bold（加粗）
	        _this.direction = "ltr";
	        _this.align = 'left';
	        _this.valign = 'top';
	        _this.fontUnderline = false;
	        _this.fontItalic = false;
	        _this.aliasName = designer_lang.aliasNameDealutValue;
	        _this.defaultAliasName = designer_lang.aliasNameDealutValue;
	        _this.defaultText = designer_lang.textDefalutValue;
	        _this.orientation = "horizontal"; //horizontal ||  vertical
	        _this.configable = true;
	        _this.zIndex = 0;
	        _this.opacity = 1;
	        _this.rotation = 0;
	        _this.editing = false;
	        _this.value = "";
	        _this.orientation = orientation;
	        _this.textDraw = new DOMTextDraw();
	        _this.control = new ControlHandle_1.DOMControlHandle(_this);
	        EventManager_1.default.register("DESINGNER_EDITTEXT_EVENT", _this.onEditText.bind(_this));
	        EventManager_1.default.register('DESINGNER_BLUR_EVENT', _this.onBulr.bind(_this));
	        if (orientation == "vertical") {
	            _this.width = 6;
	            _this.height = 34;
	        }
	        if (width !== undefined) {
	            _this.width = width;
	        }
	        if (height !== undefined) {
	            _this.height = height;
	        }
	        if (_this.height === null || _this.width === null) {
	            _this.left = 0.2;
	            _this.top = 0.2;
	        }
	        return _this;
	    }
	    Object.defineProperty(TextDrawElement.prototype, "alpha", {
	        get: function () {
	            return this.opacity;
	        },
	        set: function (value) {
	            this.opacity = value;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    TextDrawElement.prototype.onEditText = function (args) {
	        if (this.component || args.text != this) {
	            return;
	        }
	        this.editing = true;
	        var textDrawObject = this.getDrawObject();
	        textDrawObject.edit(this);
	        this.control.getDrawContext().style.pointerEvents = "none";
	    };
	    TextDrawElement.prototype.onBulr = function (args) {
	        if (this.editing) {
	            // let  textDOM = (this.getDrawObject().getDrawContext() as HTMLElement);
	            var controlDOM = this.control.getDrawContext();
	            var textDrawObject = this.getDrawObject();
	            var content = textDrawObject.getEditContent();
	            this.editing = false;
	            // textDOM.contentEditable = "false";
	            controlDOM.style.pointerEvents = "";
	            textDrawObject.blur();
	            if (content == "") {
	                content = this.defaultText;
	            }
	            this.text = content;
	            this.update();
	        }
	    };
	    TextDrawElement.prototype.getDrawObject = function () {
	        return this.textDraw;
	    };
	    TextDrawElement.prototype.getMetadata = function (key) {
	        var metadata = {
	            backgroundColor: {
	                defaultValue: "#fff",
	                value: this.backgroundColor,
	                type: String
	            },
	            fontColor: {
	                defaultValue: "#000",
	                value: this.fontColor,
	                type: String
	            },
	            letterSpacing: {
	                defaultValue: 0,
	                value: this.letterSpacing,
	                type: Number
	            },
	            lineHeight: {
	                defaultValue: "",
	                value: this.lineHeight,
	                type: String
	            },
	            fontSize: {
	                defaultValue: 8,
	                value: this.fontSize,
	                type: Number
	            },
	            orientation: {
	                defaultValue: "horizontal",
	                value: this.orientation,
	                type: String
	            },
	            fontItalic: {
	                defaultValue: false,
	                value: this.fontItalic,
	                type: Boolean
	            },
	            fontUnderline: {
	                defaultValue: false,
	                value: this.fontUnderline,
	                type: Boolean
	            },
	            fontWeight: {
	                defaultValue: "normal",
	                value: this.fontWeight,
	                type: String
	            },
	            align: {
	                defaultValue: "left",
	                value: this.align,
	                type: String
	            },
	            valign: {
	                defaultValue: "top",
	                value: this.valign,
	                type: String
	            },
	            direction: {
	                defaultValue: "ltr",
	                value: this.direction,
	                type: String
	            },
	            alpha: {
	                defaultValue: 1,
	                value: this.alpha,
	                type: Number
	            },
	            rotation: {
	                defaultValue: 0,
	                value: this.rotation,
	                type: Number
	            }
	        };
	        return metadata[key];
	    };
	    TextDrawElement.prototype.getComputedStyle = function (key) {
	        var propertyMetadata = this.getMetadata(key);
	        if (propertyMetadata.defaultValue == propertyMetadata.value) {
	            return '';
	        }
	        return key + ":" + propertyMetadata.value + ";";
	    };
	    TextDrawElement.prototype.getComputedProperty = function (key) {
	        var propertyMetadata = this.getMetadata(key);
	        if (propertyMetadata.defaultValue == propertyMetadata.value) {
	            return '';
	        }
	        return key + "=\"" + propertyMetadata.value + "\" ";
	    };
	    TextDrawElement.prototype.getTemplate = function () {
	        var aliasName = "";
	        if (this.aliasName != "" && this.aliasName != this.defaultAliasName) {
	            aliasName = "editor:_printName_=\"" + this.aliasName + "\"";
	        }
	        var rotation = "";
	        if (this.rotation) {
	            rotation = "rotation:" + this.rotation + ";";
	        }
	        var component = "";
	        if (this.component) {
	            component = "editor:component=\"" + this.component + "\"";
	        }
	        var styles = [
	            this.getComputedStyle("fontWeight"),
	            this.getComputedStyle("fontItalic"),
	            this.getComputedStyle("fontUnderline"),
	            this.getComputedStyle("orientation"),
	            this.getComputedStyle("fontSize"),
	            this.getComputedStyle("backgroundColor"),
	            this.getComputedStyle("fontColor"),
	            this.getComputedStyle('letterSpacing'),
	            this.getComputedStyle('lineHeight'),
	            this.getComputedStyle('align'),
	            this.getComputedStyle('valign'),
	            this.getComputedStyle('direction'),
	            this.getComputedStyle('alpha'),
	            this.getComputedStyle('rotation')
	        ];
	        var textValue = this.textValue;
	        if (textValue == this.defaultText) {
	            textValue = "";
	        }
	        return "\n            <text " + aliasName + " " + component + "\n                style=\"fontFamily:" + this.fontFamily + ";" + styles.join('') + "\">\n                <![CDATA[" + textValue + "]]>\n            </text>\n        ";
	    };
	    Object.defineProperty(TextDrawElement.prototype, "textValue", {
	        get: function () {
	            if (this.text == this.defaultText && this.value !== "") {
	                return this.value;
	            }
	            return this.text;
	        },
	        set: function (value) {
	            this.text = value;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    Object.defineProperty(TextDrawElement.prototype, "_printName_", {
	        get: function () {
	            return this.aliasName;
	        },
	        set: function (value) {
	            this.aliasName = value;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    TextDrawElement.prototype.clone = function () {
	        var offset = 10;
	        var cloned = _super.prototype.clone.call(this);
	        cloned.left = Number(cloned.left) + offset;
	        cloned.top = Number(cloned.top) + offset;
	        cloned.textDraw = new DOMTextDraw();
	        cloned.control = new ControlHandle_1.DOMControlHandle(cloned);
	        return cloned;
	    };
	    return TextDrawElement;
	}(DrawElement_1.DrawElement));
	exports.TextDrawElement = TextDrawElement;


/***/ }),
/* 34 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var DrawElement_1 = __webpack_require__(23);
	var Index_1 = __webpack_require__(16);
	var Const_1 = __webpack_require__(28);
	/**
	 * 使用CSS DOM 绘制文本元素
	 */
	var DOMPageDraw = (function () {
	    function DOMPageDraw() {
	        this.renderNode = Index_1.DOMUtils.CreateElement('div', { className: 'element-page' });
	    }
	    DOMPageDraw.prototype.getDrawContext = function () {
	        return this.renderNode;
	    };
	    DOMPageDraw.prototype.isActive = function () {
	        return false;
	    };
	    DOMPageDraw.prototype.remove = function () {
	    };
	    DOMPageDraw.prototype.draw = function (page) {
	        var div = this.renderNode;
	        Index_1.DOMUtils.ApplyStyle(div, {
	            position: 'relative',
	            height: Index_1.Unit.toPixel(page.height) + 'px',
	            width: Index_1.Unit.toPixel(page.width) + 'px',
	            backgroundColor: '#FFFFFF'
	        });
	    };
	    return DOMPageDraw;
	}());
	/**
	 * Page元素
	 */
	var PageDrawElement = (function (_super) {
	    __extends(PageDrawElement, _super);
	    function PageDrawElement(width, height) {
	        var _this = _super.call(this) || this;
	        _this.splitable = false;
	        _this.width = width;
	        _this.height = height;
	        _this.pageDraw = new DOMPageDraw(); ///这里应该使用工厂创建
	        return _this;
	    }
	    Object.defineProperty(PageDrawElement.prototype, "key", {
	        get: function () {
	            return this.dataset;
	        },
	        set: function (value) {
	            if (!(/^_data\./.test(value))) {
	                value = "_data." + value;
	            }
	            this.dataset = value;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    PageDrawElement.prototype.getDrawObject = function () {
	        return this.pageDraw;
	    };
	    PageDrawElement.prototype.render = function () {
	        this.getDrawObject().draw(this);
	    };
	    PageDrawElement.prototype.getScriptTemplate = function () {
	        var aliasName = [];
	        var values = [];
	        var children = this.children.sort(function (item, next) {
	            return item.left - next.left;
	        });
	        children.forEach(function (item, i) {
	            var elem = item;
	            var text = elem.forElement;
	            aliasName.push(text.aliasName != text.defaultAliasName ? text.aliasName : text.text);
	            values.push(text.text);
	        });
	        //分割符只有value，没有别名；
	        var dataset = this.dataset;
	        values = values.map(function (value) {
	            var matches = value.match(/<%=_data\.(.*)%>/);
	            if (matches != null) {
	                return "<%=" + dataset + "[i]." + matches[1] + "%>";
	            }
	            else {
	                return value;
	            }
	        });
	        this.data = { "values": values, "aliasNames": aliasName, multi: true, dataset: this.dataset, split: ';' };
	        return "\n            <layout editor:_for_=\"" + (new Date).getTime() + "\"  width=\"" + this.width + "\" height=\"" + this.height + "\"  style=\"zIndex:1;overflow:hidden;\">\n                <text  editor:_printName_=\"" + aliasName.join('') + ";\"  >\n                    <![CDATA[<% for(var i=0; i<" + this.key + ".length; ++i) { %>" + values.join('') + ";<%}%>]]>\n                </text>\n            </layout>\n        ";
	    };
	    PageDrawElement.prototype.getTemplate = function () {
	        var template = "";
	        var childrenTemplate = [];
	        var key = this.key ? "key=\"" + this.key + "\"" : "";
	        var splitable = "";
	        if (this.component && this.dataset) {
	            return this.getScriptTemplate();
	        }
	        for (var _i = 0, _a = this.children; _i < _a.length; _i++) {
	            var child = _a[_i];
	            var childElement = child;
	            childrenTemplate.push('\n\t\t' + childElement.getTemplate());
	        }
	        if (this.splitable) {
	            splitable = "splitable=\"" + this.splitable + "\"";
	        }
	        template = "<page\n        xmlns=\"" + Const_1.CLOUD_PRINT_NAME_SPACE + "\"\n        xmlns:xsi=\"" + Const_1.XSI_NAME_SPACE + "\"\n        xsi:schemaLocation=\"" + Const_1.SCHEMA_LOCATION_NAME_SPACE + "\"\n        xmlns:editor=\"" + Const_1.EDITOR_NAME_SPACE + "\"\n        width=\"" + this.width + "\" height=\"" + this.height + "\" " + (key ? "editor:" + key : "") + " " + splitable + ">" + childrenTemplate.join('') + "\n</page>";
	        return template;
	    };
	    return PageDrawElement;
	}(DrawElement_1.DrawElement));
	exports.PageDrawElement = PageDrawElement;
	//  var iterator = this.iterator;


/***/ }),
/* 35 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var DrawElement_1 = __webpack_require__(23);
	var Index_1 = __webpack_require__(16);
	var ControlHandle_1 = __webpack_require__(30);
	/**
	 * 使用CSS DOM 绘制矩形元素
	 */
	var DOMRectDraw = (function () {
	    function DOMRectDraw() {
	        this.status = "";
	        this.renderNode = Index_1.DOMUtils.CreateElement('div');
	    }
	    DOMRectDraw.prototype.getDrawContext = function () {
	        return this.renderNode;
	    };
	    DOMRectDraw.prototype.isActive = function (args) {
	        var ret = args.event.target == this.renderNode;
	        return ret;
	    };
	    DOMRectDraw.prototype.remove = function () {
	        this.status = "removed";
	        if (this.renderNode && this.renderNode.parentNode) {
	            this.renderNode.parentNode.removeChild(this.renderNode);
	        }
	    };
	    DOMRectDraw.prototype.draw = function (rectDrawElement) {
	        var div = this.renderNode;
	        var parent = rectDrawElement.parent;
	        var left = rectDrawElement.left, top = rectDrawElement.top, width = rectDrawElement.width, height = rectDrawElement.height, borderWidth = rectDrawElement.borderWidth, rotation = rectDrawElement.rotation, alpha = rectDrawElement.alpha, margin = rectDrawElement.margin, padding = rectDrawElement.padding, borderStyle = rectDrawElement.borderStyle, zIndex = rectDrawElement.zIndex, backgroundColor = rectDrawElement.backgroundColor;
	        div.className = 'element-rect';
	        Index_1.DOMUtils.ApplyStyle(div, {
	            position: 'absolute',
	            left: Index_1.Unit.toPixel(left) + 'px',
	            top: Index_1.Unit.toPixel(top) + 'px',
	            width: Index_1.Unit.toPixel(width) + 'px',
	            height: Index_1.Unit.toPixel(height) + 'px',
	            borderWidth: borderWidth + 'px',
	            boxSizing: 'border-box',
	            transform: 'rotate(' + rotation + 'deg)',
	            msTransform: 'rotate(' + rotation + 'deg)',
	            webkitTransform: 'rotate(' + rotation + 'deg)',
	            backgroundColor: backgroundColor,
	            rotation: rotation,
	            opacity: alpha,
	            margin: margin,
	            padding: padding,
	            borderStyle: borderStyle,
	            zIndex: zIndex
	        });
	        if (this.status == "" || this.status == "removed") {
	            this.status = 'render';
	            var context_1 = parent.getDrawObject().getDrawContext();
	            context_1.appendChild(this.renderNode);
	        }
	    };
	    return DOMRectDraw;
	}());
	/**
	 * 矩形元素
	 */
	var RectDrawElement = (function (_super) {
	    __extends(RectDrawElement, _super);
	    function RectDrawElement(left, top, width, height) {
	        if (left === void 0) { left = 10; }
	        if (top === void 0) { top = 10; }
	        if (width === void 0) { width = 26; }
	        if (height === void 0) { height = 26; }
	        var _this = _super.call(this) || this;
	        _this.borderWidth = 1;
	        _this.borderStyle = 'solid';
	        _this.border = "1px solid rgb(0,0,0)";
	        _this.fillColor = "#fff";
	        _this.rotation = 0;
	        _this.alpha = 1;
	        _this.zIndex = 0;
	        _this.left = left;
	        _this.top = top;
	        _this.width = width;
	        _this.height = height;
	        _this.drawObject = new DOMRectDraw();
	        _this.control = new ControlHandle_1.DOMControlHandle(_this);
	        return _this;
	    }
	    RectDrawElement.prototype.getDrawObject = function () {
	        return this.drawObject;
	    };
	    Object.defineProperty(RectDrawElement.prototype, "backgroundColor", {
	        get: function () {
	            var propertyMetadata = this.getMetadata("fillColor");
	            var ret = this.fillColor;
	            if (propertyMetadata.defaultValue == propertyMetadata.value) {
	                return '';
	            }
	            return ret;
	        },
	        set: function (value) {
	            this.fillColor = value;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    RectDrawElement.prototype.getMetadata = function (key) {
	        var metadata = {
	            borderWidth: {
	                defaultValue: 1,
	                value: this.borderWidth,
	                type: Number
	            },
	            borderStyle: {
	                defaultValue: "solid",
	                value: this.borderStyle,
	                type: String
	            },
	            fillColor: {
	                defaultValue: "#fff",
	                value: this.fillColor,
	                type: String
	            },
	            alpha: {
	                defaultValue: 1,
	                value: this.alpha,
	                type: Number
	            },
	            rotation: {
	                defaultValue: 0,
	                value: this.rotation,
	                type: Number
	            }
	        };
	        return metadata[key];
	    };
	    RectDrawElement.prototype.getComputedStyle = function (key) {
	        var propertyMetadata = this.getMetadata(key);
	        if (propertyMetadata.defaultValue == propertyMetadata.value) {
	            return '';
	        }
	        return key + ":" + propertyMetadata.value + ";";
	    };
	    RectDrawElement.prototype.getTemplate = function () {
	        var template = "<rect></rect>";
	        var style = [
	            this.getComputedStyle('rotation'),
	            this.getComputedStyle('alpha'),
	            this.getComputedStyle('borderWidth'),
	            this.getComputedStyle('borderStyle'),
	            this.getComputedStyle('fillColor')
	        ];
	        if (style.join('').length > 1) {
	            template = "<rect style=\"" + style.join('') + "\"></rect>";
	        }
	        return template;
	    };
	    RectDrawElement.prototype.clone = function () {
	        var offset = 10;
	        var cloned = _super.prototype.clone.call(this);
	        cloned.left = Number(cloned.left) + offset;
	        cloned.top = Number(cloned.top) + offset;
	        cloned.drawObject = new DOMRectDraw();
	        cloned.control = new ControlHandle_1.DOMControlHandle(cloned);
	        return cloned;
	    };
	    return RectDrawElement;
	}(DrawElement_1.DrawElement));
	exports.RectDrawElement = RectDrawElement;


/***/ }),
/* 36 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var DrawElement_1 = __webpack_require__(23);
	var Index_1 = __webpack_require__(16);
	var Dispatcher_1 = __webpack_require__(4);
	var LineControlHandle = (function () {
	    function LineControlHandle(contextElement) {
	        this.status = "";
	        this.contextElement = contextElement;
	        this.renderNode = Index_1.DOMUtils.CreateElement('div', { display: 'none' });
	        this.onPropertyChange = this.onPropertyChange.bind(this);
	        this.onSelected = this.onSelected.bind(this);
	        Dispatcher_1.EventManager.register('PROPERTY_CHANGE_EVENT', this.onPropertyChange);
	        Dispatcher_1.EventManager.register('DESINGER_SELECTED_EVENT', this.onSelected);
	    }
	    LineControlHandle.prototype.onSelected = function (args) {
	        if (args.element == this.contextElement || args.event.target == this.contextElement) {
	            if (this.status == "") {
	                this.status = 'init';
	            }
	            this.draw(this.contextElement);
	        }
	    };
	    LineControlHandle.prototype.onPropertyChange = function (args) {
	        if (args.element == this.contextElement || args.event.target == this.contextElement) {
	            this.draw(this.contextElement);
	        }
	    };
	    LineControlHandle.prototype.handle = function (ev, elements) {
	        var pointer = ev.target.getAttribute('data-pointer');
	        if (pointer) {
	            Dispatcher_1.Dispatcher.dispatch({
	                type: 'RIZE_DRAG_COMMAND',
	                data: {
	                    elements: elements,
	                    pointer: pointer
	                }
	            });
	            return;
	        }
	        Dispatcher_1.Dispatcher.dispatch({
	            type: 'MOVE_DRAG_COMMAND',
	            data: {
	                elements: elements
	            }
	        });
	    };
	    LineControlHandle.prototype.getDrawContext = function () {
	        return this.renderNode;
	    };
	    LineControlHandle.prototype.show = function () {
	        Index_1.DOMUtils.ApplyStyle(this.renderNode, {
	            display: ""
	        });
	    };
	    LineControlHandle.prototype.hide = function () {
	        Index_1.DOMUtils.ApplyStyle(this.renderNode, {
	            display: "none"
	        });
	    };
	    LineControlHandle.prototype.remove = function () {
	        this.status = "removed";
	        if (this.renderNode && this.renderNode.parentNode) {
	            this.renderNode.parentNode.removeChild(this.renderNode);
	        }
	    };
	    LineControlHandle.prototype.isActive = function (args) {
	        var target = args.event.target;
	        return target == this.renderNode || target.parentNode == this.renderNode;
	    };
	    LineControlHandle.prototype.draw = function (drawElement) {
	        var div = this.getDrawContext();
	        var left = drawElement.left, top = drawElement.top, width = drawElement.width, height = drawElement.height, length = drawElement.length, orientation = drawElement.orientation, lineWidth = drawElement.lineWidth, zIndex = drawElement.zIndex;
	        var cssStyleObject = {
	            left: Index_1.Unit.toPixel(left) + 'px',
	            top: Index_1.Unit.toPixel(top) + 'px',
	            width: Index_1.Unit.toPixel(width) + 'px',
	            height: lineWidth + 'pt',
	            position: 'absolute',
	            zIndex: zIndex
	        };
	        div.innerHTML = "\n            <span data-pointer=\"line-lm\" class=\"contorl contorl-lm\"></span>\n            <span data-pointer=\"line-rm\" class=\"contorl contorl-rm\"></span>\n        ";
	        if (orientation == 'vertical') {
	            cssStyleObject = {
	                left: Index_1.Unit.toPixel(left) + 'px',
	                top: Index_1.Unit.toPixel(top) + 'px',
	                width: lineWidth + 'pt',
	                height: Index_1.Unit.toPixel(length) + 'px',
	                position: 'absolute',
	                zIndex: zIndex
	            };
	            div.innerHTML = "\n                <span data-pointer=\"line-mt\" style=\"margin-left:-4px\" class=\"contorl contorl-mt\"></span>\n                <span data-pointer=\"line-mb\" style=\"margin-left:-4px\" class=\"contorl contorl-mb\"></span>\n            ";
	        }
	        div.className = 'contorl-handle';
	        Index_1.DOMUtils.ApplyStyle(div, cssStyleObject);
	        if (this.status == "" || this.status == "rendered") {
	            return;
	        }
	        var parent = drawElement.parent;
	        var context = parent.getDrawObject().getDrawContext();
	        var renderNode = this.getDrawContext();
	        context.appendChild(renderNode);
	        this.status = "rendered";
	    };
	    return LineControlHandle;
	}());
	var LineDraw = (function (_super) {
	    __extends(LineDraw, _super);
	    function LineDraw() {
	        var _this = _super.call(this) || this;
	        _this.status = "";
	        _this.renderNode = Index_1.DOMUtils.CreateElement('div', { className: 'element-line' });
	        return _this;
	    }
	    LineDraw.prototype.draw = function (lineDrawElement) {
	        var parent = lineDrawElement.parent;
	        var renderContext = parent && parent.getDrawObject().getDrawContext();
	        var height = lineDrawElement.height, left = lineDrawElement.left, top = lineDrawElement.top, startX = lineDrawElement.startX, startY = lineDrawElement.startY, endX = lineDrawElement.endX, endY = lineDrawElement.endY, lineWidth = lineDrawElement.lineWidth, orientation = lineDrawElement.orientation, length = lineDrawElement.length, lineType = lineDrawElement.lineType, lineColor = lineDrawElement.lineColor, zIndex = lineDrawElement.zIndex;
	        var cssStyleObject;
	        var path;
	        var strokeDasharray;
	        length = Index_1.Unit.toPixel(length);
	        left = Index_1.Unit.toPixel(left);
	        top = Index_1.Unit.toPixel(top);
	        path = "M 0 0 L" + length + " 0";
	        cssStyleObject = {
	            left: left + 'px',
	            top: top + 'px',
	            width: length + 'px',
	            borderTop: lineWidth + 'pt ' + lineType + ' ' + lineColor,
	            position: 'absolute',
	            zIndex: zIndex
	            // backgroundColor: lineColor
	        };
	        if (orientation == 'vertical') {
	            cssStyleObject = {
	                left: left + 'px',
	                top: top + 'px',
	                height: length + 'px',
	                borderLeft: lineWidth + 'pt ' + lineType + ' ' + lineColor,
	                position: 'absolute',
	                // backgroundColor: lineColor,
	                zIndex: zIndex
	            };
	            path = "M 0 0 L0 " + length;
	        }
	        Index_1.DOMUtils.ApplyStyle(this.renderNode, cssStyleObject);
	        if (lineType == 'dotted') {
	            strokeDasharray = '2,4';
	        }
	        else if (lineType == 'dashed') {
	            strokeDasharray = '8,4';
	        }
	        if (this.status == "" || this.status == "removed") {
	            renderContext.appendChild(this.renderNode);
	            this.status = 'rendered';
	        }
	    };
	    LineDraw.prototype.getDrawContext = function () {
	        return this.renderNode;
	    };
	    LineDraw.prototype.isActive = function (args) {
	        var target = args.event.target;
	        var ret = target == this.renderNode;
	        while (!ret && target) {
	            target = target.parentNode;
	            ret = target == this.renderNode;
	        }
	        return ret;
	    };
	    return LineDraw;
	}(DrawElement_1.DrawObject));
	var LineDrawElement = (function (_super) {
	    __extends(LineDrawElement, _super);
	    function LineDrawElement(orientation) {
	        var _this = _super.call(this) || this;
	        _this.startX = 5;
	        _this.startY = 5;
	        _this.endX = 25;
	        _this.endY = 5;
	        _this.lineColor = "#000";
	        _this.lineType = "solid";
	        _this.lineWidth = 1;
	        _this.orientation = orientation;
	        _this.control = new LineControlHandle(_this);
	        _this.drawObject = new LineDraw();
	        if (_this.orientation == 'vertical') {
	            _this.endX = 5;
	            _this.endY = 25;
	        }
	        return _this;
	    }
	    LineDrawElement.prototype.getMetadata = function (key) {
	        var metadata = {
	            lineWidth: {
	                defaultValue: 1,
	                value: this.lineWidth,
	                type: Number
	            }
	        };
	        return metadata[key];
	    };
	    Object.defineProperty(LineDrawElement.prototype, "left", {
	        get: function () {
	            return this.startX;
	        },
	        set: function (value) {
	            var offset = Number(value) - this.startX;
	            var endX = Number(this.endX);
	            value = Number(value);
	            endX = Number(Number(endX + offset).toFixed(2));
	            this.endX = endX;
	            this.startX = value;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    Object.defineProperty(LineDrawElement.prototype, "top", {
	        get: function () {
	            return this.startY;
	        },
	        set: function (value) {
	            var endY = Number(this.endY);
	            var offset = Number(value) - this.startY;
	            value = Number(value);
	            endY = Number(Number(endY + offset).toFixed(2));
	            this.endY = endY;
	            this.startY = value;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    Object.defineProperty(LineDrawElement.prototype, "width", {
	        get: function () {
	            if (this.orientation == 'vertical') {
	                return this.lineWidth;
	            }
	            else {
	                return Math.abs(this.startX - this.endX);
	            }
	        },
	        set: function (value) {
	            this.endX = Number(value) + Number(this.startX);
	        },
	        enumerable: true,
	        configurable: true
	    });
	    Object.defineProperty(LineDrawElement.prototype, "length", {
	        get: function () {
	            if (this.orientation == 'vertical') {
	                return Math.abs(this.startY - this.endY);
	            }
	            else {
	                return Math.abs(this.startX - this.endX);
	            }
	        },
	        enumerable: true,
	        configurable: true
	    });
	    Object.defineProperty(LineDrawElement.prototype, "height", {
	        get: function () {
	            if (this.orientation == 'vertical') {
	                return Math.abs(this.startY - this.endY);
	            }
	            else {
	                return this.lineWidth;
	            }
	        },
	        enumerable: true,
	        configurable: true
	    });
	    LineDrawElement.prototype.getDrawObject = function () {
	        return this.drawObject;
	    };
	    LineDrawElement.prototype.getComputedStyle = function (key) {
	        var propertyMetadata = this.getMetadata(key);
	        if (propertyMetadata.defaultValue == propertyMetadata.value) {
	            return '';
	        }
	        return key + ":" + propertyMetadata.value + ";";
	    };
	    Object.defineProperty(LineDrawElement.prototype, "_deg_", {
	        get: function () {
	            if (this.orientation == 'vertical') {
	                return '90';
	            }
	            return '';
	        },
	        set: function (value) {
	            if (Number(value) == 90) {
	                this.orientation = 'vertical';
	            }
	        },
	        enumerable: true,
	        configurable: true
	    });
	    LineDrawElement.prototype.getTemplate = function () {
	        var style = 'lineType:' + this.lineType + ';' + 'lineColor:' + this.lineColor + ';';
	        style += this.getComputedStyle('lineWidth');
	        var deg = "";
	        if (this._deg_) {
	            deg = "editor:_deg_=\"" + this._deg_ + "\"";
	        }
	        return ("<line\n                    style=\"" + style + "\"\n                    startX=\"" + this.startX + "\" \n                    startY=\"" + this.startY + "\" \n                    endX=\"" + this.endX + "\"\n                    endY=\"" + this.endY + "\"\n                    " + deg + ">\n                </line>\n                ");
	    };
	    LineDrawElement.prototype.clone = function () {
	        var offset = 10;
	        var cloned = _super.prototype.clone.call(this);
	        cloned.left = Number(cloned.left) + offset;
	        cloned.top = Number(cloned.top) + offset;
	        cloned.drawObject = new LineDraw();
	        cloned.control = new LineControlHandle(cloned);
	        return cloned;
	    };
	    return LineDrawElement;
	}(DrawElement_1.DrawElement));
	exports.LineDrawElement = LineDrawElement;


/***/ }),
/* 37 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var DrawElement_1 = __webpack_require__(23);
	var Index_1 = __webpack_require__(16);
	var ControlHandle_1 = __webpack_require__(30);
	//非法图片
	var illegalImageUrlDom = document.getElementById("J_illegalImageUrls");
	var illegalImageUrls = JSON.parse(illegalImageUrlDom.value) || [];
	/**
	 * 使用CSS DOM 绘制矩形元素
	 */
	var DOMImageDraw = (function () {
	    function DOMImageDraw(imgsrc) {
	        this.status = "";
	        this.renderNode = Index_1.DOMUtils.CreateElement('div');
	        var imgDOM = Index_1.DOMUtils.CreateElement('img');
	        imgDOM.setAttribute("src", imgsrc);
	        var illegal = this.isIllegalImageUrls(imgsrc);
	        this.renderNode.appendChild(imgDOM);
	        //若是非法图片
	        if (illegal) {
	            var illegalTipDom = Index_1.DOMUtils.CreateElement('div');
	            illegalTipDom.innerHTML = "非法图片";
	            Index_1.DOMUtils.ApplyStyle(illegalTipDom, {
	                width: '100%',
	                height: '100%',
	                position: 'absolute',
	                backgroundColor: 'rgba(0,0,0,0.7)',
	                color: 'red',
	                textAlign: 'center',
	                top: '0px',
	                fontSize: '18px'
	            });
	            this.renderNode.appendChild(illegalTipDom);
	        }
	    }
	    DOMImageDraw.prototype.getDrawContext = function () {
	        return this.renderNode;
	    };
	    DOMImageDraw.prototype.isIllegalImageUrls = function (imgSrc) {
	        var illegal = false;
	        illegalImageUrls.forEach(function (item) {
	            if (imgSrc == item) {
	                illegal = true;
	                return false;
	            }
	        });
	        return illegal;
	    };
	    DOMImageDraw.prototype.isActive = function (args) {
	        var targetDom = args.event.target;
	        //事件不支持冒泡，这里判断一下中间的图片是否被选中
	        if (targetDom.parentNode == this.renderNode) {
	            targetDom = args.event.target.parentNode;
	        }
	        var ret = targetDom == this.renderNode;
	        return ret;
	    };
	    DOMImageDraw.prototype.remove = function () {
	        this.status = "removed";
	        if (this.renderNode && this.renderNode.parentNode) {
	            this.renderNode.parentNode.removeChild(this.renderNode);
	        }
	    };
	    DOMImageDraw.prototype.draw = function (imgDrawElement) {
	        var div = this.renderNode;
	        var parent = imgDrawElement.parent;
	        var left = imgDrawElement.left, top = imgDrawElement.top, width = imgDrawElement.width, height = imgDrawElement.height, rotation = imgDrawElement.rotation, alpha = imgDrawElement.alpha, zIndex = imgDrawElement.zIndex;
	        div.className = 'element-image';
	        Index_1.DOMUtils.ApplyStyle(div, {
	            position: 'absolute',
	            left: Index_1.Unit.toPixel(left) + 'px',
	            top: Index_1.Unit.toPixel(top) + 'px',
	            width: Index_1.Unit.toPixel(width) + 'px',
	            height: Index_1.Unit.toPixel(height) + 'px',
	            boxSizing: 'border-box',
	            transform: 'rotate(' + rotation + 'deg)',
	            msTransform: 'rotate(' + rotation + 'deg)',
	            webkitTransform: 'rotate(' + rotation + 'deg)',
	            oTransform: 'rotate(' + rotation + 'deg)',
	            mozTransform: 'rotate(' + rotation + 'deg)',
	            opacity: alpha,
	            zIndex: zIndex
	        });
	        if (this.status == "" || this.status == "removed") {
	            this.status = 'render';
	            var context_1 = parent.getDrawObject().getDrawContext();
	            context_1.appendChild(this.renderNode);
	        }
	    };
	    return DOMImageDraw;
	}());
	/**
	 * 矩形元素
	 */
	var ImageDrawElement = (function (_super) {
	    __extends(ImageDrawElement, _super);
	    function ImageDrawElement(src, left, top, width, height, page) {
	        if (left === void 0) { left = 10; }
	        if (top === void 0) { top = 10; }
	        var _this = _super.call(this) || this;
	        _this.rotation = 0;
	        _this.alpha = 1;
	        _this.zIndex = 0;
	        _this.imgsrc = src;
	        _this.left = left ? left : 10;
	        _this.top = top ? left : 10;
	        _this.pagesize = page ? { width: page.width, height: page.height } : { width: 100, height: 180 };
	        var limitSize;
	        if (width && height) {
	            limitSize = _this.getLimitSize(_this.pagesize, {
	                width: width,
	                height: height
	            });
	        }
	        else {
	            limitSize = _this.getLimitSize(_this.pagesize, {
	                width: 50,
	                height: 50
	            });
	        }
	        _this.width = limitSize.width;
	        _this.height = limitSize.height;
	        _this.drawObject = new DOMImageDraw(src);
	        _this.control = new ControlHandle_1.DOMControlHandle(_this);
	        return _this;
	    }
	    ImageDrawElement.prototype.getDrawObject = function () {
	        return this.drawObject;
	    };
	    ImageDrawElement.prototype.getTemplate = function () {
	        var styles = [];
	        var style = "";
	        var rotation = "";
	        if (this.rotation) {
	            styles.push("rotation:" + this.rotation + ";");
	        }
	        if (this.alpha != 1) {
	            styles.push("alpha:" + this.alpha + ";");
	        }
	        if (styles.length > 0) {
	            style = "style=\"" + styles.join('') + "\"";
	        }
	        var template = "<image src=\"" + this.imgsrc + "\" " + style + " />";
	        return template;
	    };
	    /**
	     * 图片原始宽高获取
	     */
	    ImageDrawElement.prototype.getImgNaturalDimensions = function (imgsrc) {
	        var image = new Image();
	        image.src = imgsrc;
	        //单位转换
	        return {
	            width: Index_1.Unit.toMillimeter(image.width),
	            height: Index_1.Unit.toMillimeter(image.height)
	        };
	    };
	    /**
	     *  图片宽高的判断
	     */
	    ImageDrawElement.prototype.getLimitSize = function (pagesize, imgSize) {
	        //图片宽高比例
	        var iWhRate = imgSize.width / imgSize.height;
	        //画布宽高
	        var pageSize = pagesize;
	        //画布宽高比例
	        var pWhRate = pageSize.width / pageSize.height;
	        //最终限制宽高
	        var limitSize = imgSize;
	        if (iWhRate >= pWhRate) {
	            //图片宽度更大，限制大小以图片宽度为参考
	            if (imgSize.width >= pageSize.width) {
	                limitSize.width = pageSize.width;
	                limitSize.height = pageSize.width / iWhRate;
	            }
	        }
	        else {
	            //图片高度更大，限制大小以图片高度为参考
	            if (imgSize.height >= pageSize.height) {
	                limitSize.height = pageSize.height;
	                limitSize.width = pageSize.height * iWhRate;
	            }
	        }
	        limitSize.width = limitSize.width > 0 ? limitSize.width : 50;
	        limitSize.height = limitSize.height > 0 ? limitSize.height : 50;
	        return limitSize;
	    };
	    ImageDrawElement.prototype.clone = function () {
	        var offset = 10;
	        var cloned = _super.prototype.clone.call(this);
	        cloned.left = parseFloat(cloned.left) + offset;
	        cloned.top = parseFloat(cloned.top) + offset;
	        cloned.drawObject = new DOMImageDraw(cloned.imgsrc);
	        cloned.control = new ControlHandle_1.DOMControlHandle(cloned);
	        return cloned;
	    };
	    return ImageDrawElement;
	}(DrawElement_1.DrawElement));
	exports.ImageDrawElement = ImageDrawElement;


/***/ }),
/* 38 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var DrawElement_1 = __webpack_require__(23);
	var Index_1 = __webpack_require__(16);
	var ControlHandle_1 = __webpack_require__(30);
	var JsBarcode = __webpack_require__(39);
	/**
	 * 使用CSS DOM 绘制矩形元素
	 */
	var DOMBarcodeDraw = (function () {
	    function DOMBarcodeDraw(barcodeDrawElement) {
	        this.status = "";
	        //barcode的type类型大小写映射集合
	        this.barcodeTypeListMap = {
	            'code128': 'code128',
	            'code93': 'code93',
	            'code39': 'code39',
	            'upca': 'UPC-A',
	            'upce': 'UPC-E',
	            'ean8': 'EAN8',
	            'ean13': 'EAN13',
	            'itf14': 'ITF14',
	            'c25inter': 'Interleaved 2 of 5'
	        };
	        this.renderNode = Index_1.DOMUtils.CreateElement('div');
	        this.barcodeDrawElement = barcodeDrawElement;
	        this.updateBarcode();
	    }
	    DOMBarcodeDraw.prototype.getDrawContext = function () {
	        return this.renderNode;
	    };
	    DOMBarcodeDraw.prototype.isActive = function (args) {
	        var targetDom = args.event.target;
	        //事件不支持冒泡，这里判断一下中间的图片是否被选中
	        if (targetDom.parentNode == this.renderNode) {
	            targetDom = args.event.target.parentNode;
	        }
	        var ret = targetDom == this.renderNode;
	        return ret;
	    };
	    DOMBarcodeDraw.prototype.remove = function () {
	        this.status = "removed";
	        if (this.renderNode && this.renderNode.parentNode) {
	            this.renderNode.parentNode.removeChild(this.renderNode);
	        }
	    };
	    DOMBarcodeDraw.prototype.draw = function (barcodeDrawElement) {
	        var div = this.renderNode;
	        var parent = barcodeDrawElement.parent;
	        var left = barcodeDrawElement.left, top = barcodeDrawElement.top, width = barcodeDrawElement.width, height = barcodeDrawElement.height, alpha = barcodeDrawElement.alpha, zIndex = barcodeDrawElement.zIndex, rotation = barcodeDrawElement.rotation;
	        div.className = 'element-barcode';
	        Index_1.DOMUtils.ApplyStyle(div, {
	            position: 'absolute',
	            left: Index_1.Unit.toPixel(left) + 'px',
	            top: Index_1.Unit.toPixel(top) + 'px',
	            width: Index_1.Unit.toPixel(width) + 'px',
	            height: Index_1.Unit.toPixel(height) + 'px',
	            boxSizing: 'border-box',
	            opacity: alpha,
	            zIndex: zIndex,
	            transform: 'rotate(' + rotation + 'deg)',
	            msTransform: 'rotate(' + rotation + 'deg)',
	            webkitTransform: 'rotate(' + rotation + 'deg)'
	        });
	        if (this.status == "" || this.status == "removed") {
	            this.status = 'render';
	            var context_1 = parent.getDrawObject().getDrawContext();
	            context_1.appendChild(this.renderNode);
	        }
	    };
	    DOMBarcodeDraw.prototype.updateBarcode = function () {
	        var imgNode = Index_1.DOMUtils.CreateElement('img');
	        var barcodeDrawElement = this.barcodeDrawElement, code_type = this.barcodeTypeListMap[barcodeDrawElement.type], code_value = barcodeDrawElement.value;
	        if (code_type == 'UPC-A' || code_type == 'UPC-E') {
	            code_value = '123456789999';
	            code_type = 'UPC'; //统一做UPC渲染
	        }
	        else if (code_type == 'ITF14') {
	            code_value = '10012345000017';
	        }
	        else if (code_type == 'EAN8') {
	            code_value = '12345670';
	        }
	        else if (code_type == 'EAN13') {
	            code_value = '1234567890128';
	        }
	        else if (code_type == 'code93' || code_type == 'Interleaved 2 of 5') {
	            code_value = 'Example 1234';
	            code_type = 'code128'; //统一做code128渲染            
	        }
	        else if (code_type == 'code128') {
	            code_value = 'Example 1234';
	        }
	        else if (code_type == 'code39') {
	            code_value = 'EXAMPLE TEXT';
	        }
	        JsBarcode(imgNode, code_value, {
	            format: code_type,
	            margin: 0,
	            displayValue: !barcodeDrawElement.hideText
	        });
	        this.renderNode.innerHTML = "";
	        this.renderNode.appendChild(imgNode);
	    };
	    return DOMBarcodeDraw;
	}());
	/**
	 * 矩形元素
	 */
	var BarcodeDrawElement = (function (_super) {
	    __extends(BarcodeDrawElement, _super);
	    function BarcodeDrawElement(value, left, top, width, height, page) {
	        if (left === void 0) { left = 10; }
	        if (top === void 0) { top = 10; }
	        var _this = _super.call(this) || this;
	        _this.defaultValue = "12345678";
	        _this.type = "code128";
	        _this.alpha = 1;
	        _this.zIndex = 0;
	        _this.width = 40;
	        _this.height = 24;
	        _this.hideText = true;
	        _this.configable = true;
	        _this.rotation = 0;
	        _this.ratioMode = "keepRatio";
	        _this.value = value ? value : _this.defaultValue;
	        _this.left = left ? left : 10;
	        _this.top = top ? left : 10;
	        _this.pagesize = page ? { width: page.width, height: page.height } : { width: 100, height: 180 };
	        var limitSize;
	        if (width && height) {
	            _this.width = width;
	            _this.height = height;
	        }
	        limitSize = _this.getLimitSize(_this.pagesize, {
	            width: _this.width,
	            height: _this.height
	        });
	        _this.width = limitSize.width;
	        _this.height = limitSize.height;
	        _this.drawObject = new DOMBarcodeDraw(_this);
	        _this.control = new ControlHandle_1.DOMControlHandle(_this);
	        return _this;
	    }
	    BarcodeDrawElement.prototype.getDrawObject = function () {
	        return this.drawObject;
	    };
	    BarcodeDrawElement.prototype.getTemplate = function () {
	        // this.drawObject.updateBarcode();
	        var style = "opacity:" + this.alpha + ";hideText:" + this.hideText + ";rotation:" + this.rotation;
	        var optionProp_primary, optionProp_schema;
	        var template = "<barcode type=\"" + this.type + "\" style=\"" + style + "\" value=\"" + this.value + "\" ratioMode=\"" + this.ratioMode + "\" />";
	        return template;
	    };
	    /**
	     *  图片宽高的判断
	     */
	    BarcodeDrawElement.prototype.getLimitSize = function (pagesize, imgSize) {
	        //图片宽高比例
	        var iWhRate = imgSize.width / imgSize.height;
	        //画布宽高
	        var pageSize = pagesize;
	        //画布宽高比例
	        var pWhRate = pageSize.width / pageSize.height;
	        //最终限制宽高
	        var limitSize = imgSize;
	        if (iWhRate >= pWhRate) {
	            //图片宽度更大，限制大小以图片宽度为参考
	            if (imgSize.width >= pageSize.width) {
	                limitSize.width = pageSize.width;
	                limitSize.height = pageSize.width / iWhRate;
	            }
	        }
	        else {
	            //图片高度更大，限制大小以图片高度为参考
	            if (imgSize.height >= pageSize.height) {
	                limitSize.height = pageSize.height;
	                limitSize.width = pageSize.height * iWhRate;
	            }
	        }
	        limitSize.width = limitSize.width > 0 ? limitSize.width : 50;
	        limitSize.height = limitSize.height > 0 ? limitSize.height : 50;
	        return limitSize;
	    };
	    BarcodeDrawElement.prototype.clone = function () {
	        var offset = 10;
	        var cloned = _super.prototype.clone.call(this);
	        cloned.left = parseFloat(cloned.left) + offset;
	        cloned.top = parseFloat(cloned.top) + offset;
	        cloned.drawObject = new DOMBarcodeDraw(cloned);
	        cloned.control = new ControlHandle_1.DOMControlHandle(cloned);
	        return cloned;
	    };
	    return BarcodeDrawElement;
	}(DrawElement_1.DrawElement));
	exports.BarcodeDrawElement = BarcodeDrawElement;


/***/ }),
/* 39 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	
	var _barcodes = __webpack_require__(40);
	
	var _barcodes2 = _interopRequireDefault(_barcodes);
	
	var _merge = __webpack_require__(68);
	
	var _merge2 = _interopRequireDefault(_merge);
	
	var _linearizeEncodings = __webpack_require__(69);
	
	var _linearizeEncodings2 = _interopRequireDefault(_linearizeEncodings);
	
	var _fixOptions = __webpack_require__(70);
	
	var _fixOptions2 = _interopRequireDefault(_fixOptions);
	
	var _getRenderProperties = __webpack_require__(71);
	
	var _getRenderProperties2 = _interopRequireDefault(_getRenderProperties);
	
	var _optionsFromStrings = __webpack_require__(73);
	
	var _optionsFromStrings2 = _interopRequireDefault(_optionsFromStrings);
	
	var _ErrorHandler = __webpack_require__(80);
	
	var _ErrorHandler2 = _interopRequireDefault(_ErrorHandler);
	
	var _exceptions = __webpack_require__(79);
	
	var _defaults = __webpack_require__(74);
	
	var _defaults2 = _interopRequireDefault(_defaults);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	// The protype of the object returned from the JsBarcode() call
	
	
	// Help functions
	var API = function API() {};
	
	// The first call of the library API
	// Will return an object with all barcodes calls and the data that is used
	// by the renderers
	
	
	// Default values
	
	
	// Exceptions
	// Import all the barcodes
	var JsBarcode = function JsBarcode(element, text, options) {
		var api = new API();
	
		if (typeof element === "undefined") {
			throw Error("No element to render on was provided.");
		}
	
		// Variables that will be pased through the API calls
		api._renderProperties = (0, _getRenderProperties2.default)(element);
		api._encodings = [];
		api._options = _defaults2.default;
		api._errorHandler = new _ErrorHandler2.default(api);
	
		// If text is set, use the simple syntax (render the barcode directly)
		if (typeof text !== "undefined") {
			options = options || {};
	
			if (!options.format) {
				options.format = autoSelectBarcode();
			}
	
			api.options(options)[options.format](text, options).render();
		}
	
		return api;
	};
	
	// To make tests work TODO: remove
	JsBarcode.getModule = function (name) {
		return _barcodes2.default[name];
	};
	
	// Register all barcodes
	for (var name in _barcodes2.default) {
		if (_barcodes2.default.hasOwnProperty(name)) {
			// Security check if the propery is a prototype property
			registerBarcode(_barcodes2.default, name);
		}
	}
	function registerBarcode(barcodes, name) {
		API.prototype[name] = API.prototype[name.toUpperCase()] = API.prototype[name.toLowerCase()] = function (text, options) {
			var api = this;
			return api._errorHandler.wrapBarcodeCall(function () {
				var newOptions = (0, _merge2.default)(api._options, options);
				newOptions = (0, _optionsFromStrings2.default)(newOptions);
				var Encoder = barcodes[name];
				var encoded = encode(text, Encoder, newOptions);
				api._encodings.push(encoded);
	
				return api;
			});
		};
	}
	
	// encode() handles the Encoder call and builds the binary string to be rendered
	function encode(text, Encoder, options) {
		// Ensure that text is a string
		text = "" + text;
	
		var encoder = new Encoder(text, options);
	
		// If the input is not valid for the encoder, throw error.
		// If the valid callback option is set, call it instead of throwing error
		if (!encoder.valid()) {
			throw new _exceptions.InvalidInputException(encoder.constructor.name, text);
		}
	
		// Make a request for the binary data (and other infromation) that should be rendered
		var encoded = encoder.encode();
	
		// Encodings can be nestled like [[1-1, 1-2], 2, [3-1, 3-2]
		// Convert to [1-1, 1-2, 2, 3-1, 3-2]
		encoded = (0, _linearizeEncodings2.default)(encoded);
	
		// Merge
		for (var i = 0; i < encoded.length; i++) {
			encoded[i].options = (0, _merge2.default)(options, encoded[i].options);
		}
	
		return encoded;
	}
	
	function autoSelectBarcode() {
		// If CODE128 exists. Use it
		if (_barcodes2.default["CODE128"]) {
			return "CODE128";
		}
	
		// Else, take the first (probably only) barcode
		return Object.keys(_barcodes2.default)[0];
	}
	
	// Sets global encoder options
	// Added to the api by the JsBarcode function
	API.prototype.options = function (options) {
		this._options = (0, _merge2.default)(this._options, options);
		return this;
	};
	
	// Will create a blank space (usually in between barcodes)
	API.prototype.blank = function (size) {
		var zeroes = "0".repeat(size);
		this._encodings.push({ data: zeroes });
		return this;
	};
	
	// Initialize JsBarcode on all HTML elements defined.
	API.prototype.init = function () {
		// Should do nothing if no elements where found
		if (!this._renderProperties) {
			return;
		}
	
		// Make sure renderProperies is an array
		if (!Array.isArray(this._renderProperties)) {
			this._renderProperties = [this._renderProperties];
		}
	
		var renderProperty;
		for (var i in this._renderProperties) {
			renderProperty = this._renderProperties[i];
			var options = (0, _merge2.default)(this._options, renderProperty.options);
	
			if (options.format == "auto") {
				options.format = autoSelectBarcode();
			}
	
			this._errorHandler.wrapBarcodeCall(function () {
				var text = options.value;
				var Encoder = _barcodes2.default[options.format.toUpperCase()];
				var encoded = encode(text, Encoder, options);
	
				render(renderProperty, encoded, options);
			});
		}
	};
	
	// The render API call. Calls the real render function.
	API.prototype.render = function () {
		if (!this._renderProperties) {
			throw new _exceptions.NoElementException();
		}
	
		if (Array.isArray(this._renderProperties)) {
			for (var i = 0; i < this._renderProperties.length; i++) {
				render(this._renderProperties[i], this._encodings, this._options);
			}
		} else {
			render(this._renderProperties, this._encodings, this._options);
		}
	
		return this;
	};
	
	API.prototype._defaults = _defaults2.default;
	
	// Prepares the encodings and calls the renderer
	function render(renderProperties, encodings, options) {
		encodings = (0, _linearizeEncodings2.default)(encodings);
	
		for (var i = 0; i < encodings.length; i++) {
			encodings[i].options = (0, _merge2.default)(options, encodings[i].options);
			(0, _fixOptions2.default)(encodings[i].options);
		}
	
		(0, _fixOptions2.default)(options);
	
		var Renderer = renderProperties.renderer;
		var renderer = new Renderer(renderProperties.element, encodings, options);
		renderer.render();
	
		if (renderProperties.afterRender) {
			renderProperties.afterRender();
		}
	}
	
	// Export to browser
	if (typeof window !== "undefined") {
		window.JsBarcode = JsBarcode;
	}
	
	// Export to jQuery
	/*global jQuery */
	if (typeof jQuery !== 'undefined') {
		jQuery.fn.JsBarcode = function (content, options) {
			var elementArray = [];
			jQuery(this).each(function () {
				elementArray.push(this);
			});
			return JsBarcode(elementArray, content, options);
		};
	}
	
	// Export to commonJS
	module.exports = JsBarcode;

/***/ }),
/* 40 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _CODE = __webpack_require__(41);
	
	var _CODE2 = __webpack_require__(43);
	
	var _EAN_UPC = __webpack_require__(49);
	
	var _ITF = __webpack_require__(56);
	
	var _ITF2 = __webpack_require__(57);
	
	var _MSI = __webpack_require__(58);
	
	var _pharmacode = __webpack_require__(65);
	
	var _codabar = __webpack_require__(66);
	
	var _GenericBarcode = __webpack_require__(67);
	
	exports.default = {
		CODE39: _CODE.CODE39,
		CODE128: _CODE2.CODE128, CODE128A: _CODE2.CODE128A, CODE128B: _CODE2.CODE128B, CODE128C: _CODE2.CODE128C,
		EAN13: _EAN_UPC.EAN13, EAN8: _EAN_UPC.EAN8, EAN5: _EAN_UPC.EAN5, EAN2: _EAN_UPC.EAN2, UPC: _EAN_UPC.UPC,
		ITF14: _ITF.ITF14,
		ITF: _ITF2.ITF,
		MSI: _MSI.MSI, MSI10: _MSI.MSI10, MSI11: _MSI.MSI11, MSI1010: _MSI.MSI1010, MSI1110: _MSI.MSI1110,
		pharmacode: _pharmacode.pharmacode,
		codabar: _codabar.codabar,
		GenericBarcode: _GenericBarcode.GenericBarcode
	};

/***/ }),
/* 41 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	exports.CODE39 = undefined;
	
	var _Barcode2 = __webpack_require__(42);
	
	var _Barcode3 = _interopRequireDefault(_Barcode2);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; } // Encoding documentation:
	// https://en.wikipedia.org/wiki/Code_39#Encoding
	
	var CODE39 = function (_Barcode) {
		_inherits(CODE39, _Barcode);
	
		function CODE39(data, options) {
			_classCallCheck(this, CODE39);
	
			data = data.toUpperCase();
	
			// Calculate mod43 checksum if enabled
			if (options.mod43) {
				data += getCharacter(mod43checksum(data));
			}
	
			return _possibleConstructorReturn(this, _Barcode.call(this, data, options));
		}
	
		CODE39.prototype.encode = function encode() {
			// First character is always a *
			var result = getEncoding("*");
	
			// Take every character and add the binary representation to the result
			for (var i = 0; i < this.data.length; i++) {
				result += getEncoding(this.data[i]) + "0";
			}
	
			// Last character is always a *
			result += getEncoding("*");
	
			return {
				data: result,
				text: this.text
			};
		};
	
		CODE39.prototype.valid = function valid() {
			return this.data.search(/^[0-9A-Z\-\.\ \$\/\+\%]+$/) !== -1;
		};
	
		return CODE39;
	}(_Barcode3.default);
	
	// All characters. The position in the array is the (checksum) value
	
	
	var characters = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "-", ".", " ", "$", "/", "+", "%", "*"];
	
	// The decimal representation of the characters, is converted to the
	// corresponding binary with the getEncoding function
	var encodings = [20957, 29783, 23639, 30485, 20951, 29813, 23669, 20855, 29789, 23645, 29975, 23831, 30533, 22295, 30149, 24005, 21623, 29981, 23837, 22301, 30023, 23879, 30545, 22343, 30161, 24017, 21959, 30065, 23921, 22385, 29015, 18263, 29141, 17879, 29045, 18293, 17783, 29021, 18269, 17477, 17489, 17681, 20753, 35770];
	
	// Get the binary representation of a character by converting the encodings
	// from decimal to binary
	function getEncoding(character) {
		return getBinary(characterValue(character));
	}
	
	function getBinary(characterValue) {
		return encodings[characterValue].toString(2);
	}
	
	function getCharacter(characterValue) {
		return characters[characterValue];
	}
	
	function characterValue(character) {
		return characters.indexOf(character);
	}
	
	function mod43checksum(data) {
		var checksum = 0;
		for (var i = 0; i < data.length; i++) {
			checksum += characterValue(data[i]);
		}
	
		checksum = checksum % 43;
		return checksum;
	}
	
	exports.CODE39 = CODE39;

/***/ }),
/* 42 */
/***/ (function(module, exports) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	var Barcode = function Barcode(data, options) {
		_classCallCheck(this, Barcode);
	
		this.data = data;
		this.text = options.text || data;
		this.options = options;
	};
	
	exports.default = Barcode;

/***/ }),
/* 43 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.CODE128C = exports.CODE128B = exports.CODE128A = exports.CODE128 = undefined;
	
	var _CODE128_AUTO = __webpack_require__(44);
	
	var _CODE128_AUTO2 = _interopRequireDefault(_CODE128_AUTO);
	
	var _CODE128A = __webpack_require__(46);
	
	var _CODE128A2 = _interopRequireDefault(_CODE128A);
	
	var _CODE128B = __webpack_require__(47);
	
	var _CODE128B2 = _interopRequireDefault(_CODE128B);
	
	var _CODE128C = __webpack_require__(48);
	
	var _CODE128C2 = _interopRequireDefault(_CODE128C);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.CODE128 = _CODE128_AUTO2.default;
	exports.CODE128A = _CODE128A2.default;
	exports.CODE128B = _CODE128B2.default;
	exports.CODE128C = _CODE128C2.default;

/***/ }),
/* 44 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _CODE2 = __webpack_require__(45);
	
	var _CODE3 = _interopRequireDefault(_CODE2);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }
	
	var CODE128AUTO = function (_CODE) {
		_inherits(CODE128AUTO, _CODE);
	
		function CODE128AUTO(data, options) {
			_classCallCheck(this, CODE128AUTO);
	
			// ASCII value ranges 0-127, 200-211
			if (data.search(/^[\x00-\x7F\xC8-\xD3]+$/) !== -1) {
				var _this = _possibleConstructorReturn(this, _CODE.call(this, autoSelectModes(data), options));
			} else {
				var _this = _possibleConstructorReturn(this, _CODE.call(this, data, options));
			}
			return _possibleConstructorReturn(_this);
		}
	
		return CODE128AUTO;
	}(_CODE3.default);
	
	function autoSelectModes(string) {
		// ASCII ranges 0-98 and 200-207 (FUNCs and SHIFTs)
		var aLength = string.match(/^[\x00-\x5F\xC8-\xCF]*/)[0].length;
		// ASCII ranges 32-127 and 200-207 (FUNCs and SHIFTs)
		var bLength = string.match(/^[\x20-\x7F\xC8-\xCF]*/)[0].length;
		// Number pairs or [FNC1]
		var cLength = string.match(/^(\xCF*[0-9]{2}\xCF*)*/)[0].length;
	
		var newString;
		// Select CODE128C if the string start with enough digits
		if (cLength >= 2) {
			newString = String.fromCharCode(210) + autoSelectFromC(string);
		}
		// Select A/C depending on the longest match
		else if (aLength > bLength) {
				newString = String.fromCharCode(208) + autoSelectFromA(string);
			} else {
				newString = String.fromCharCode(209) + autoSelectFromB(string);
			}
	
		newString = newString.replace(/[\xCD\xCE]([^])[\xCD\xCE]/, function (match, char) {
			return String.fromCharCode(203) + char;
		});
	
		return newString;
	}
	
	function autoSelectFromA(string) {
		var untilC = string.match(/^([\x00-\x5F\xC8-\xCF]+?)(([0-9]{2}){2,})([^0-9]|$)/);
	
		if (untilC) {
			return untilC[1] + String.fromCharCode(204) + autoSelectFromC(string.substring(untilC[1].length));
		}
	
		var aChars = string.match(/^[\x00-\x5F\xC8-\xCF]+/);
		if (aChars[0].length === string.length) {
			return string;
		}
	
		return aChars[0] + String.fromCharCode(205) + autoSelectFromB(string.substring(aChars[0].length));
	}
	
	function autoSelectFromB(string) {
		var untilC = string.match(/^([\x20-\x7F\xC8-\xCF]+?)(([0-9]{2}){2,})([^0-9]|$)/);
	
		if (untilC) {
			return untilC[1] + String.fromCharCode(204) + autoSelectFromC(string.substring(untilC[1].length));
		}
	
		var bChars = string.match(/^[\x20-\x7F\xC8-\xCF]+/);
		if (bChars[0].length === string.length) {
			return string;
		}
	
		return bChars[0] + String.fromCharCode(206) + autoSelectFromA(string.substring(bChars[0].length));
	}
	
	function autoSelectFromC(string) {
		var cMatch = string.match(/^(\xCF*[0-9]{2}\xCF*)+/)[0];
		var length = cMatch.length;
	
		if (length === string.length) {
			return string;
		}
	
		string = string.substring(length);
	
		// Select A/B depending on the longest match
		var aLength = string.match(/^[\x00-\x5F\xC8-\xCF]*/)[0].length;
		var bLength = string.match(/^[\x20-\x7F\xC8-\xCF]*/)[0].length;
		if (aLength >= bLength) {
			return cMatch + String.fromCharCode(206) + autoSelectFromA(string);
		} else {
			return cMatch + String.fromCharCode(205) + autoSelectFromB(string);
		}
	}
	
	exports.default = CODE128AUTO;

/***/ }),
/* 45 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _Barcode2 = __webpack_require__(42);
	
	var _Barcode3 = _interopRequireDefault(_Barcode2);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; } // This is the master class, it does require the start code to be
	// included in the string
	
	var CODE128 = function (_Barcode) {
		_inherits(CODE128, _Barcode);
	
		function CODE128(data, options) {
			_classCallCheck(this, CODE128);
	
			// Fill the bytes variable with the ascii codes of string
			var _this = _possibleConstructorReturn(this, _Barcode.call(this, data.substring(1), options));
	
			_this.bytes = [];
			for (var i = 0; i < data.length; ++i) {
				_this.bytes.push(data.charCodeAt(i));
			}
	
			// Data for each character, the last characters will not be encoded but are used for error correction
			// Numbers encode to (n + 1000) -> binary; 740 -> (740 + 1000).toString(2) -> "11011001100"
			_this.encodings = [// + 1000
			740, 644, 638, 176, 164, 100, 224, 220, 124, 608, 604, 572, 436, 244, 230, 484, 260, 254, 650, 628, 614, 764, 652, 902, 868, 836, 830, 892, 844, 842, 752, 734, 590, 304, 112, 94, 416, 128, 122, 672, 576, 570, 464, 422, 134, 496, 478, 142, 910, 678, 582, 768, 762, 774, 880, 862, 814, 896, 890, 818, 914, 602, 930, 328, 292, 200, 158, 68, 62, 424, 412, 232, 218, 76, 74, 554, 616, 978, 556, 146, 340, 212, 182, 508, 268, 266, 956, 940, 938, 758, 782, 974, 400, 310, 118, 512, 506, 960, 954, 502, 518, 886, 966, /* Start codes */668, 680, 692, 5379];
			return _this;
		}
	
		// The public encoding function
	
	
		CODE128.prototype.encode = function encode() {
			var encodingResult;
			var bytes = this.bytes;
			// Remove the startcode from the bytes and set its index
			var startIndex = bytes.shift() - 105;
	
			// Start encode with the right type
			if (startIndex === 103) {
				encodingResult = this.nextA(bytes, 1);
			} else if (startIndex === 104) {
				encodingResult = this.nextB(bytes, 1);
			} else if (startIndex === 105) {
				encodingResult = this.nextC(bytes, 1);
			}
	
			return {
				text: this.text == this.data ? this.text.replace(/[^\x20-\x7E]/g, "") : this.text,
				data:
				// Add the start bits
				this.getEncoding(startIndex) +
				// Add the encoded bits
				encodingResult.result +
				// Add the checksum
				this.getEncoding((encodingResult.checksum + startIndex) % 103) +
				// Add the end bits
				this.getEncoding(106)
			};
		};
	
		CODE128.prototype.getEncoding = function getEncoding(n) {
			return this.encodings[n] ? (this.encodings[n] + 1000).toString(2) : '';
		};
	
		// Use the regexp variable for validation
	
	
		CODE128.prototype.valid = function valid() {
			// ASCII value ranges 0-127, 200-211
			return this.data.search(/^[\x00-\x7F\xC8-\xD3]+$/) !== -1;
		};
	
		CODE128.prototype.nextA = function nextA(bytes, depth) {
			if (bytes.length <= 0) {
				return { "result": "", "checksum": 0 };
			}
	
			var next, index;
	
			// Special characters
			if (bytes[0] >= 200) {
				index = bytes[0] - 105;
	
				// Remove first element
				bytes.shift();
	
				// Swap to CODE128C
				if (index === 99) {
					next = this.nextC(bytes, depth + 1);
				}
				// Swap to CODE128B
				else if (index === 100) {
						next = this.nextB(bytes, depth + 1);
					}
					// Shift
					else if (index === 98) {
							// Convert the next character so that is encoded correctly
							bytes[0] = bytes[0] > 95 ? bytes[0] - 96 : bytes[0];
							next = this.nextA(bytes, depth + 1);
						}
						// Continue on CODE128A but encode a special character
						else {
								next = this.nextA(bytes, depth + 1);
							}
			}
			// Continue encoding of CODE128A
			else {
					var charCode = bytes[0];
					index = charCode < 32 ? charCode + 64 : charCode - 32;
	
					// Remove first element
					bytes.shift();
	
					next = this.nextA(bytes, depth + 1);
				}
	
			// Get the correct binary encoding and calculate the weight
			var enc = this.getEncoding(index);
			var weight = index * depth;
	
			return {
				"result": enc + next.result,
				"checksum": weight + next.checksum
			};
		};
	
		CODE128.prototype.nextB = function nextB(bytes, depth) {
			if (bytes.length <= 0) {
				return { "result": "", "checksum": 0 };
			}
	
			var next, index;
	
			// Special characters
			if (bytes[0] >= 200) {
				index = bytes[0] - 105;
	
				// Remove first element
				bytes.shift();
	
				// Swap to CODE128C
				if (index === 99) {
					next = this.nextC(bytes, depth + 1);
				}
				// Swap to CODE128A
				else if (index === 101) {
						next = this.nextA(bytes, depth + 1);
					}
					// Shift
					else if (index === 98) {
							// Convert the next character so that is encoded correctly
							bytes[0] = bytes[0] < 32 ? bytes[0] + 96 : bytes[0];
							next = this.nextB(bytes, depth + 1);
						}
						// Continue on CODE128B but encode a special character
						else {
								next = this.nextB(bytes, depth + 1);
							}
			}
			// Continue encoding of CODE128B
			else {
					index = bytes[0] - 32;
					bytes.shift();
					next = this.nextB(bytes, depth + 1);
				}
	
			// Get the correct binary encoding and calculate the weight
			var enc = this.getEncoding(index);
			var weight = index * depth;
	
			return { "result": enc + next.result, "checksum": weight + next.checksum };
		};
	
		CODE128.prototype.nextC = function nextC(bytes, depth) {
			if (bytes.length <= 0) {
				return { "result": "", "checksum": 0 };
			}
	
			var next, index;
	
			// Special characters
			if (bytes[0] >= 200) {
				index = bytes[0] - 105;
	
				// Remove first element
				bytes.shift();
	
				// Swap to CODE128B
				if (index === 100) {
					next = this.nextB(bytes, depth + 1);
				}
				// Swap to CODE128A
				else if (index === 101) {
						next = this.nextA(bytes, depth + 1);
					}
					// Continue on CODE128C but encode a special character
					else {
							next = this.nextC(bytes, depth + 1);
						}
			}
			// Continue encoding of CODE128C
			else {
					index = (bytes[0] - 48) * 10 + bytes[1] - 48;
					bytes.shift();
					bytes.shift();
					next = this.nextC(bytes, depth + 1);
				}
	
			// Get the correct binary encoding and calculate the weight
			var enc = this.getEncoding(index);
			var weight = index * depth;
	
			return { "result": enc + next.result, "checksum": weight + next.checksum };
		};
	
		return CODE128;
	}(_Barcode3.default);
	
	exports.default = CODE128;

/***/ }),
/* 46 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _CODE2 = __webpack_require__(45);
	
	var _CODE3 = _interopRequireDefault(_CODE2);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }
	
	var CODE128A = function (_CODE) {
		_inherits(CODE128A, _CODE);
	
		function CODE128A(string, options) {
			_classCallCheck(this, CODE128A);
	
			return _possibleConstructorReturn(this, _CODE.call(this, String.fromCharCode(208) + string, options));
		}
	
		CODE128A.prototype.valid = function valid() {
			return this.data.search(/^[\x00-\x5F\xC8-\xCF]+$/) !== -1;
		};
	
		return CODE128A;
	}(_CODE3.default);
	
	exports.default = CODE128A;

/***/ }),
/* 47 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _CODE2 = __webpack_require__(45);
	
	var _CODE3 = _interopRequireDefault(_CODE2);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }
	
	var CODE128B = function (_CODE) {
		_inherits(CODE128B, _CODE);
	
		function CODE128B(string, options) {
			_classCallCheck(this, CODE128B);
	
			return _possibleConstructorReturn(this, _CODE.call(this, String.fromCharCode(209) + string, options));
		}
	
		CODE128B.prototype.valid = function valid() {
			return this.data.search(/^[\x20-\x7F\xC8-\xCF]+$/) !== -1;
		};
	
		return CODE128B;
	}(_CODE3.default);
	
	exports.default = CODE128B;

/***/ }),
/* 48 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _CODE2 = __webpack_require__(45);
	
	var _CODE3 = _interopRequireDefault(_CODE2);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }
	
	var CODE128C = function (_CODE) {
		_inherits(CODE128C, _CODE);
	
		function CODE128C(string, options) {
			_classCallCheck(this, CODE128C);
	
			return _possibleConstructorReturn(this, _CODE.call(this, String.fromCharCode(210) + string, options));
		}
	
		CODE128C.prototype.valid = function valid() {
			return this.data.search(/^(\xCF*[0-9]{2}\xCF*)+$/) !== -1;
		};
	
		return CODE128C;
	}(_CODE3.default);
	
	exports.default = CODE128C;

/***/ }),
/* 49 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.UPC = exports.EAN2 = exports.EAN5 = exports.EAN8 = exports.EAN13 = undefined;
	
	var _EAN = __webpack_require__(50);
	
	var _EAN2 = _interopRequireDefault(_EAN);
	
	var _EAN3 = __webpack_require__(52);
	
	var _EAN4 = _interopRequireDefault(_EAN3);
	
	var _EAN5 = __webpack_require__(53);
	
	var _EAN6 = _interopRequireDefault(_EAN5);
	
	var _EAN7 = __webpack_require__(54);
	
	var _EAN8 = _interopRequireDefault(_EAN7);
	
	var _UPC = __webpack_require__(55);
	
	var _UPC2 = _interopRequireDefault(_UPC);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.EAN13 = _EAN2.default;
	exports.EAN8 = _EAN4.default;
	exports.EAN5 = _EAN6.default;
	exports.EAN2 = _EAN8.default;
	exports.UPC = _UPC2.default;

/***/ }),
/* 50 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _ean_encoder = __webpack_require__(51);
	
	var _ean_encoder2 = _interopRequireDefault(_ean_encoder);
	
	var _Barcode2 = __webpack_require__(42);
	
	var _Barcode3 = _interopRequireDefault(_Barcode2);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; } // Encoding documentation:
	// https://en.wikipedia.org/wiki/International_Article_Number_(EAN)#Binary_encoding_of_data_digits_into_EAN-13_barcode
	
	var EAN13 = function (_Barcode) {
		_inherits(EAN13, _Barcode);
	
		function EAN13(data, options) {
			_classCallCheck(this, EAN13);
	
			// Add checksum if it does not exist
			if (data.search(/^[0-9]{12}$/) !== -1) {
				data += checksum(data);
			}
	
			// Make sure the font is not bigger than the space between the guard bars
			var _this = _possibleConstructorReturn(this, _Barcode.call(this, data, options));
	
			if (!options.flat && options.fontSize > options.width * 10) {
				_this.fontSize = options.width * 10;
			} else {
				_this.fontSize = options.fontSize;
			}
	
			// Make the guard bars go down half the way of the text
			_this.guardHeight = options.height + _this.fontSize / 2 + options.textMargin;
	
			// Adds a last character to the end of the barcode
			_this.lastChar = options.lastChar;
			return _this;
		}
	
		EAN13.prototype.valid = function valid() {
			return this.data.search(/^[0-9]{13}$/) !== -1 && this.data[12] == checksum(this.data);
		};
	
		EAN13.prototype.encode = function encode() {
			if (this.options.flat) {
				return this.flatEncoding();
			} else {
				return this.guardedEncoding();
			}
		};
	
		// Define the EAN-13 structure
	
	
		EAN13.prototype.getStructure = function getStructure() {
			return ["LLLLLL", "LLGLGG", "LLGGLG", "LLGGGL", "LGLLGG", "LGGLLG", "LGGGLL", "LGLGLG", "LGLGGL", "LGGLGL"];
		};
	
		// The "standard" way of printing EAN13 barcodes with guard bars
	
	
		EAN13.prototype.guardedEncoding = function guardedEncoding() {
			var encoder = new _ean_encoder2.default();
			var result = [];
	
			var structure = this.getStructure()[this.data[0]];
	
			// Get the string to be encoded on the left side of the EAN code
			var leftSide = this.data.substr(1, 6);
	
			// Get the string to be encoded on the right side of the EAN code
			var rightSide = this.data.substr(7, 6);
	
			// Add the first digigt
			if (this.options.displayValue) {
				result.push({
					data: "000000000000",
					text: this.text.substr(0, 1),
					options: { textAlign: "left", fontSize: this.fontSize }
				});
			}
	
			// Add the guard bars
			result.push({
				data: "101",
				options: { height: this.guardHeight }
			});
	
			// Add the left side
			result.push({
				data: encoder.encode(leftSide, structure),
				text: this.text.substr(1, 6),
				options: { fontSize: this.fontSize }
			});
	
			// Add the middle bits
			result.push({
				data: "01010",
				options: { height: this.guardHeight }
			});
	
			// Add the right side
			result.push({
				data: encoder.encode(rightSide, "RRRRRR"),
				text: this.text.substr(7, 6),
				options: { fontSize: this.fontSize }
			});
	
			// Add the end bits
			result.push({
				data: "101",
				options: { height: this.guardHeight }
			});
	
			if (this.options.lastChar && this.options.displayValue) {
				result.push({ data: "00" });
	
				result.push({
					data: "00000",
					text: this.options.lastChar,
					options: { fontSize: this.fontSize }
				});
			}
			return result;
		};
	
		EAN13.prototype.flatEncoding = function flatEncoding() {
			var encoder = new _ean_encoder2.default();
			var result = "";
	
			var structure = this.getStructure()[this.data[0]];
	
			result += "101";
			result += encoder.encode(this.data.substr(1, 6), structure);
			result += "01010";
			result += encoder.encode(this.data.substr(7, 6), "RRRRRR");
			result += "101";
	
			return {
				data: result,
				text: this.text
			};
		};
	
		return EAN13;
	}(_Barcode3.default);
	
	// Calulate the checksum digit
	// https://en.wikipedia.org/wiki/International_Article_Number_(EAN)#Calculation_of_checksum_digit
	
	
	function checksum(number) {
		var result = 0;
	
		var i;
		for (i = 0; i < 12; i += 2) {
			result += parseInt(number[i]);
		}
		for (i = 1; i < 12; i += 2) {
			result += parseInt(number[i]) * 3;
		}
	
		return (10 - result % 10) % 10;
	}
	
	exports.default = EAN13;

/***/ }),
/* 51 */
/***/ (function(module, exports) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	var EANencoder = function () {
		function EANencoder() {
			_classCallCheck(this, EANencoder);
	
			// Standard start end and middle bits
			this.startBin = "101";
			this.endBin = "101";
			this.middleBin = "01010";
	
			// The L (left) type of encoding
			this.Lbinary = ["0001101", "0011001", "0010011", "0111101", "0100011", "0110001", "0101111", "0111011", "0110111", "0001011"];
	
			// The G type of encoding
			this.Gbinary = ["0100111", "0110011", "0011011", "0100001", "0011101", "0111001", "0000101", "0010001", "0001001", "0010111"];
	
			// The R (right) type of encoding
			this.Rbinary = ["1110010", "1100110", "1101100", "1000010", "1011100", "1001110", "1010000", "1000100", "1001000", "1110100"];
		}
	
		// Convert a numberarray to the representing
	
	
		EANencoder.prototype.encode = function encode(number, structure, separator) {
			// Create the variable that should be returned at the end of the function
			var result = "";
	
			// Make sure that the separator is set
			separator = separator || "";
	
			// Loop all the numbers
			for (var i = 0; i < number.length; i++) {
				// Using the L, G or R encoding and add it to the returning variable
				if (structure[i] == "L") {
					result += this.Lbinary[number[i]];
				} else if (structure[i] == "G") {
					result += this.Gbinary[number[i]];
				} else if (structure[i] == "R") {
					result += this.Rbinary[number[i]];
				}
	
				// Add separator in between encodings
				if (i < number.length - 1) {
					result += separator;
				}
			}
			return result;
		};
	
		return EANencoder;
	}();
	
	exports.default = EANencoder;

/***/ }),
/* 52 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _ean_encoder = __webpack_require__(51);
	
	var _ean_encoder2 = _interopRequireDefault(_ean_encoder);
	
	var _Barcode2 = __webpack_require__(42);
	
	var _Barcode3 = _interopRequireDefault(_Barcode2);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; } // Encoding documentation:
	// http://www.barcodeisland.com/ean8.phtml
	
	var EAN8 = function (_Barcode) {
		_inherits(EAN8, _Barcode);
	
		function EAN8(data, options) {
			_classCallCheck(this, EAN8);
	
			// Add checksum if it does not exist
			if (data.search(/^[0-9]{7}$/) !== -1) {
				data += checksum(data);
			}
	
			return _possibleConstructorReturn(this, _Barcode.call(this, data, options));
		}
	
		EAN8.prototype.valid = function valid() {
			return this.data.search(/^[0-9]{8}$/) !== -1 && this.data[7] == checksum(this.data);
		};
	
		EAN8.prototype.encode = function encode() {
			var encoder = new _ean_encoder2.default();
	
			// Create the return variable
			var result = "";
	
			// Get the number to be encoded on the left side of the EAN code
			var leftSide = this.data.substr(0, 4);
	
			// Get the number to be encoded on the right side of the EAN code
			var rightSide = this.data.substr(4, 4);
	
			// Add the start bits
			result += encoder.startBin;
	
			// Add the left side
			result += encoder.encode(leftSide, "LLLL");
	
			// Add the middle bits
			result += encoder.middleBin;
	
			// Add the right side
			result += encoder.encode(rightSide, "RRRR");
	
			// Add the end bits
			result += encoder.endBin;
	
			return {
				data: result,
				text: this.text
			};
		};
	
		return EAN8;
	}(_Barcode3.default);
	
	// Calulate the checksum digit
	
	
	function checksum(number) {
		var result = 0;
	
		var i;
		for (i = 0; i < 7; i += 2) {
			result += parseInt(number[i]) * 3;
		}
	
		for (i = 1; i < 7; i += 2) {
			result += parseInt(number[i]);
		}
	
		return (10 - result % 10) % 10;
	}
	
	exports.default = EAN8;

/***/ }),
/* 53 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _ean_encoder = __webpack_require__(51);
	
	var _ean_encoder2 = _interopRequireDefault(_ean_encoder);
	
	var _Barcode2 = __webpack_require__(42);
	
	var _Barcode3 = _interopRequireDefault(_Barcode2);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; } // Encoding documentation:
	// https://en.wikipedia.org/wiki/EAN_5#Encoding
	
	var EAN5 = function (_Barcode) {
		_inherits(EAN5, _Barcode);
	
		function EAN5(data, options) {
			_classCallCheck(this, EAN5);
	
			// Define the EAN-13 structure
			var _this = _possibleConstructorReturn(this, _Barcode.call(this, data, options));
	
			_this.structure = ["GGLLL", "GLGLL", "GLLGL", "GLLLG", "LGGLL", "LLGGL", "LLLGG", "LGLGL", "LGLLG", "LLGLG"];
			return _this;
		}
	
		EAN5.prototype.valid = function valid() {
			return this.data.search(/^[0-9]{5}$/) !== -1;
		};
	
		EAN5.prototype.encode = function encode() {
			var encoder = new _ean_encoder2.default();
			var checksum = this.checksum();
	
			// Start bits
			var result = "1011";
	
			// Use normal ean encoding with 01 in between all digits
			result += encoder.encode(this.data, this.structure[checksum], "01");
	
			return {
				data: result,
				text: this.text
			};
		};
	
		EAN5.prototype.checksum = function checksum() {
			var result = 0;
	
			result += parseInt(this.data[0]) * 3;
			result += parseInt(this.data[1]) * 9;
			result += parseInt(this.data[2]) * 3;
			result += parseInt(this.data[3]) * 9;
			result += parseInt(this.data[4]) * 3;
	
			return result % 10;
		};
	
		return EAN5;
	}(_Barcode3.default);
	
	exports.default = EAN5;

/***/ }),
/* 54 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _ean_encoder = __webpack_require__(51);
	
	var _ean_encoder2 = _interopRequireDefault(_ean_encoder);
	
	var _Barcode2 = __webpack_require__(42);
	
	var _Barcode3 = _interopRequireDefault(_Barcode2);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; } // Encoding documentation:
	// https://en.wikipedia.org/wiki/EAN_2#Encoding
	
	var EAN2 = function (_Barcode) {
		_inherits(EAN2, _Barcode);
	
		function EAN2(data, options) {
			_classCallCheck(this, EAN2);
	
			var _this = _possibleConstructorReturn(this, _Barcode.call(this, data, options));
	
			_this.structure = ["LL", "LG", "GL", "GG"];
			return _this;
		}
	
		EAN2.prototype.valid = function valid() {
			return this.data.search(/^[0-9]{2}$/) !== -1;
		};
	
		EAN2.prototype.encode = function encode() {
			var encoder = new _ean_encoder2.default();
	
			// Choose the structure based on the number mod 4
			var structure = this.structure[parseInt(this.data) % 4];
	
			// Start bits
			var result = "1011";
	
			// Encode the two digits with 01 in between
			result += encoder.encode(this.data, structure, "01");
	
			return {
				data: result,
				text: this.text
			};
		};
	
		return EAN2;
	}(_Barcode3.default);
	
	exports.default = EAN2;

/***/ }),
/* 55 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _ean_encoder = __webpack_require__(51);
	
	var _ean_encoder2 = _interopRequireDefault(_ean_encoder);
	
	var _Barcode2 = __webpack_require__(42);
	
	var _Barcode3 = _interopRequireDefault(_Barcode2);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; } // Encoding documentation:
	// https://en.wikipedia.org/wiki/Universal_Product_Code#Encoding
	
	var UPC = function (_Barcode) {
		_inherits(UPC, _Barcode);
	
		function UPC(data, options) {
			_classCallCheck(this, UPC);
	
			// Add checksum if it does not exist
			if (data.search(/^[0-9]{11}$/) !== -1) {
				data += checksum(data);
			}
	
			var _this = _possibleConstructorReturn(this, _Barcode.call(this, data, options));
	
			_this.displayValue = options.displayValue;
	
			// Make sure the font is not bigger than the space between the guard bars
			if (options.fontSize > options.width * 10) {
				_this.fontSize = options.width * 10;
			} else {
				_this.fontSize = options.fontSize;
			}
	
			// Make the guard bars go down half the way of the text
			_this.guardHeight = options.height + _this.fontSize / 2 + options.textMargin;
			return _this;
		}
	
		UPC.prototype.valid = function valid() {
			return this.data.search(/^[0-9]{12}$/) !== -1 && this.data[11] == checksum(this.data);
		};
	
		UPC.prototype.encode = function encode() {
			if (this.options.flat) {
				return this.flatEncoding();
			} else {
				return this.guardedEncoding();
			}
		};
	
		UPC.prototype.flatEncoding = function flatEncoding() {
			var encoder = new _ean_encoder2.default();
			var result = "";
	
			result += "101";
			result += encoder.encode(this.data.substr(0, 6), "LLLLLL");
			result += "01010";
			result += encoder.encode(this.data.substr(6, 6), "RRRRRR");
			result += "101";
	
			return {
				data: result,
				text: this.text
			};
		};
	
		UPC.prototype.guardedEncoding = function guardedEncoding() {
			var encoder = new _ean_encoder2.default();
			var result = [];
	
			// Add the first digigt
			if (this.displayValue) {
				result.push({
					data: "00000000",
					text: this.text.substr(0, 1),
					options: { textAlign: "left", fontSize: this.fontSize }
				});
			}
	
			// Add the guard bars
			result.push({
				data: "101" + encoder.encode(this.data[0], "L"),
				options: { height: this.guardHeight }
			});
	
			// Add the left side
			result.push({
				data: encoder.encode(this.data.substr(1, 5), "LLLLL"),
				text: this.text.substr(1, 5),
				options: { fontSize: this.fontSize }
			});
	
			// Add the middle bits
			result.push({
				data: "01010",
				options: { height: this.guardHeight }
			});
	
			// Add the right side
			result.push({
				data: encoder.encode(this.data.substr(6, 5), "RRRRR"),
				text: this.text.substr(6, 5),
				options: { fontSize: this.fontSize }
			});
	
			// Add the end bits
			result.push({
				data: encoder.encode(this.data[11], "R") + "101",
				options: { height: this.guardHeight }
			});
	
			// Add the last digit
			if (this.displayValue) {
				result.push({
					data: "00000000",
					text: this.text.substr(11, 1),
					options: { textAlign: "right", fontSize: this.fontSize }
				});
			}
	
			return result;
		};
	
		return UPC;
	}(_Barcode3.default);
	
	// Calulate the checksum digit
	// https://en.wikipedia.org/wiki/International_Article_Number_(EAN)#Calculation_of_checksum_digit
	
	
	function checksum(number) {
		var result = 0;
	
		var i;
		for (i = 1; i < 11; i += 2) {
			result += parseInt(number[i]);
		}
		for (i = 0; i < 11; i += 2) {
			result += parseInt(number[i]) * 3;
		}
	
		return (10 - result % 10) % 10;
	}
	
	exports.default = UPC;

/***/ }),
/* 56 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	exports.ITF14 = undefined;
	
	var _Barcode2 = __webpack_require__(42);
	
	var _Barcode3 = _interopRequireDefault(_Barcode2);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }
	
	var ITF14 = function (_Barcode) {
		_inherits(ITF14, _Barcode);
	
		function ITF14(data, options) {
			_classCallCheck(this, ITF14);
	
			// Add checksum if it does not exist
			if (data.search(/^[0-9]{13}$/) !== -1) {
				data += checksum(data);
			}
	
			var _this = _possibleConstructorReturn(this, _Barcode.call(this, data, options));
	
			_this.binaryRepresentation = {
				"0": "00110",
				"1": "10001",
				"2": "01001",
				"3": "11000",
				"4": "00101",
				"5": "10100",
				"6": "01100",
				"7": "00011",
				"8": "10010",
				"9": "01010"
			};
			return _this;
		}
	
		ITF14.prototype.valid = function valid() {
			return this.data.search(/^[0-9]{14}$/) !== -1 && this.data[13] == checksum(this.data);
		};
	
		ITF14.prototype.encode = function encode() {
			var result = "1010";
	
			// Calculate all the digit pairs
			for (var i = 0; i < 14; i += 2) {
				result += this.calculatePair(this.data.substr(i, 2));
			}
	
			// Always add the same end bits
			result += "11101";
	
			return {
				data: result,
				text: this.text
			};
		};
	
		// Calculate the data of a number pair
	
	
		ITF14.prototype.calculatePair = function calculatePair(numberPair) {
			var result = "";
	
			var number1Struct = this.binaryRepresentation[numberPair[0]];
			var number2Struct = this.binaryRepresentation[numberPair[1]];
	
			// Take every second bit and add to the result
			for (var i = 0; i < 5; i++) {
				result += number1Struct[i] == "1" ? "111" : "1";
				result += number2Struct[i] == "1" ? "000" : "0";
			}
	
			return result;
		};
	
		return ITF14;
	}(_Barcode3.default);
	
	// Calulate the checksum digit
	
	
	function checksum(data) {
		var result = 0;
	
		for (var i = 0; i < 13; i++) {
			result += parseInt(data[i]) * (3 - i % 2 * 2);
		}
	
		return Math.ceil(result / 10) * 10 - result;
	}
	
	exports.ITF14 = ITF14;

/***/ }),
/* 57 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	exports.ITF = undefined;
	
	var _Barcode2 = __webpack_require__(42);
	
	var _Barcode3 = _interopRequireDefault(_Barcode2);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }
	
	var ITF = function (_Barcode) {
		_inherits(ITF, _Barcode);
	
		function ITF(data, options) {
			_classCallCheck(this, ITF);
	
			var _this = _possibleConstructorReturn(this, _Barcode.call(this, data, options));
	
			_this.binaryRepresentation = {
				"0": "00110",
				"1": "10001",
				"2": "01001",
				"3": "11000",
				"4": "00101",
				"5": "10100",
				"6": "01100",
				"7": "00011",
				"8": "10010",
				"9": "01010"
			};
			return _this;
		}
	
		ITF.prototype.valid = function valid() {
			return this.data.search(/^([0-9]{2})+$/) !== -1;
		};
	
		ITF.prototype.encode = function encode() {
			// Always add the same start bits
			var result = "1010";
	
			// Calculate all the digit pairs
			for (var i = 0; i < this.data.length; i += 2) {
				result += this.calculatePair(this.data.substr(i, 2));
			}
	
			// Always add the same end bits
			result += "11101";
	
			return {
				data: result,
				text: this.text
			};
		};
	
		// Calculate the data of a number pair
	
	
		ITF.prototype.calculatePair = function calculatePair(numberPair) {
			var result = "";
	
			var number1Struct = this.binaryRepresentation[numberPair[0]];
			var number2Struct = this.binaryRepresentation[numberPair[1]];
	
			// Take every second bit and add to the result
			for (var i = 0; i < 5; i++) {
				result += number1Struct[i] == "1" ? "111" : "1";
				result += number2Struct[i] == "1" ? "000" : "0";
			}
	
			return result;
		};
	
		return ITF;
	}(_Barcode3.default);
	
	exports.ITF = ITF;

/***/ }),
/* 58 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.MSI1110 = exports.MSI1010 = exports.MSI11 = exports.MSI10 = exports.MSI = undefined;
	
	var _MSI = __webpack_require__(59);
	
	var _MSI2 = _interopRequireDefault(_MSI);
	
	var _MSI3 = __webpack_require__(60);
	
	var _MSI4 = _interopRequireDefault(_MSI3);
	
	var _MSI5 = __webpack_require__(62);
	
	var _MSI6 = _interopRequireDefault(_MSI5);
	
	var _MSI7 = __webpack_require__(63);
	
	var _MSI8 = _interopRequireDefault(_MSI7);
	
	var _MSI9 = __webpack_require__(64);
	
	var _MSI10 = _interopRequireDefault(_MSI9);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.MSI = _MSI2.default;
	exports.MSI10 = _MSI4.default;
	exports.MSI11 = _MSI6.default;
	exports.MSI1010 = _MSI8.default;
	exports.MSI1110 = _MSI10.default;

/***/ }),
/* 59 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _Barcode2 = __webpack_require__(42);
	
	var _Barcode3 = _interopRequireDefault(_Barcode2);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; } // Encoding documentation
	// https://en.wikipedia.org/wiki/MSI_Barcode#Character_set_and_binary_lookup
	
	var MSI = function (_Barcode) {
		_inherits(MSI, _Barcode);
	
		function MSI(data, options) {
			_classCallCheck(this, MSI);
	
			return _possibleConstructorReturn(this, _Barcode.call(this, data, options));
		}
	
		MSI.prototype.encode = function encode() {
			// Start bits
			var ret = "110";
	
			for (var i = 0; i < this.data.length; i++) {
				// Convert the character to binary (always 4 binary digits)
				var digit = parseInt(this.data[i]);
				var bin = digit.toString(2);
				bin = addZeroes(bin, 4 - bin.length);
	
				// Add 100 for every zero and 110 for every 1
				for (var b = 0; b < bin.length; b++) {
					ret += bin[b] == "0" ? "100" : "110";
				}
			}
	
			// End bits
			ret += "1001";
	
			return {
				data: ret,
				text: this.text
			};
		};
	
		MSI.prototype.valid = function valid() {
			return this.data.search(/^[0-9]+$/) !== -1;
		};
	
		return MSI;
	}(_Barcode3.default);
	
	function addZeroes(number, n) {
		for (var i = 0; i < n; i++) {
			number = "0" + number;
		}
		return number;
	}
	
	exports.default = MSI;

/***/ }),
/* 60 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _MSI2 = __webpack_require__(59);
	
	var _MSI3 = _interopRequireDefault(_MSI2);
	
	var _checksums = __webpack_require__(61);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }
	
	var MSI10 = function (_MSI) {
		_inherits(MSI10, _MSI);
	
		function MSI10(data, options) {
			_classCallCheck(this, MSI10);
	
			return _possibleConstructorReturn(this, _MSI.call(this, data + (0, _checksums.mod10)(data), options));
		}
	
		return MSI10;
	}(_MSI3.default);
	
	exports.default = MSI10;

/***/ }),
/* 61 */
/***/ (function(module, exports) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	exports.mod10 = mod10;
	exports.mod11 = mod11;
	function mod10(number) {
		var sum = 0;
		for (var i = 0; i < number.length; i++) {
			var n = parseInt(number[i]);
			if ((i + number.length) % 2 === 0) {
				sum += n;
			} else {
				sum += n * 2 % 10 + Math.floor(n * 2 / 10);
			}
		}
		return (10 - sum % 10) % 10;
	}
	
	function mod11(number) {
		var sum = 0;
		var weights = [2, 3, 4, 5, 6, 7];
		for (var i = 0; i < number.length; i++) {
			var n = parseInt(number[number.length - 1 - i]);
			sum += weights[i % weights.length] * n;
		}
		return (11 - sum % 11) % 11;
	}

/***/ }),
/* 62 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _MSI2 = __webpack_require__(59);
	
	var _MSI3 = _interopRequireDefault(_MSI2);
	
	var _checksums = __webpack_require__(61);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }
	
	var MSI11 = function (_MSI) {
		_inherits(MSI11, _MSI);
	
		function MSI11(data, options) {
			_classCallCheck(this, MSI11);
	
			return _possibleConstructorReturn(this, _MSI.call(this, data + (0, _checksums.mod11)(data), options));
		}
	
		return MSI11;
	}(_MSI3.default);
	
	exports.default = MSI11;

/***/ }),
/* 63 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _MSI2 = __webpack_require__(59);
	
	var _MSI3 = _interopRequireDefault(_MSI2);
	
	var _checksums = __webpack_require__(61);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }
	
	var MSI1010 = function (_MSI) {
		_inherits(MSI1010, _MSI);
	
		function MSI1010(data, options) {
			_classCallCheck(this, MSI1010);
	
			data += (0, _checksums.mod10)(data);
			data += (0, _checksums.mod10)(data);
			return _possibleConstructorReturn(this, _MSI.call(this, data, options));
		}
	
		return MSI1010;
	}(_MSI3.default);
	
	exports.default = MSI1010;

/***/ }),
/* 64 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _MSI2 = __webpack_require__(59);
	
	var _MSI3 = _interopRequireDefault(_MSI2);
	
	var _checksums = __webpack_require__(61);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }
	
	var MSI1110 = function (_MSI) {
		_inherits(MSI1110, _MSI);
	
		function MSI1110(data, options) {
			_classCallCheck(this, MSI1110);
	
			data += (0, _checksums.mod11)(data);
			data += (0, _checksums.mod10)(data);
			return _possibleConstructorReturn(this, _MSI.call(this, data, options));
		}
	
		return MSI1110;
	}(_MSI3.default);
	
	exports.default = MSI1110;

/***/ }),
/* 65 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	exports.pharmacode = undefined;
	
	var _Barcode2 = __webpack_require__(42);
	
	var _Barcode3 = _interopRequireDefault(_Barcode2);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; } // Encoding documentation
	// http://www.gomaro.ch/ftproot/Laetus_PHARMA-CODE.pdf
	
	var pharmacode = function (_Barcode) {
		_inherits(pharmacode, _Barcode);
	
		function pharmacode(data, options) {
			_classCallCheck(this, pharmacode);
	
			var _this = _possibleConstructorReturn(this, _Barcode.call(this, data, options));
	
			_this.number = parseInt(data, 10);
			return _this;
		}
	
		pharmacode.prototype.encode = function encode() {
			var z = this.number;
			var result = "";
	
			// http://i.imgur.com/RMm4UDJ.png
			// (source: http://www.gomaro.ch/ftproot/Laetus_PHARMA-CODE.pdf, page: 34)
			while (!isNaN(z) && z != 0) {
				if (z % 2 === 0) {
					// Even
					result = "11100" + result;
					z = (z - 2) / 2;
				} else {
					// Odd
					result = "100" + result;
					z = (z - 1) / 2;
				}
			}
	
			// Remove the two last zeroes
			result = result.slice(0, -2);
	
			return {
				data: result,
				text: this.text
			};
		};
	
		pharmacode.prototype.valid = function valid() {
			return this.number >= 3 && this.number <= 131070;
		};
	
		return pharmacode;
	}(_Barcode3.default);
	
	exports.pharmacode = pharmacode;

/***/ }),
/* 66 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	exports.codabar = undefined;
	
	var _Barcode2 = __webpack_require__(42);
	
	var _Barcode3 = _interopRequireDefault(_Barcode2);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; } // Encoding specification:
	// http://www.barcodeisland.com/codabar.phtml
	
	var codabar = function (_Barcode) {
		_inherits(codabar, _Barcode);
	
		function codabar(data, options) {
			_classCallCheck(this, codabar);
	
			if (data.search(/^[0-9\-\$\:\.\+\/]+$/) === 0) {
				data = "A" + data + "A";
			}
	
			var _this = _possibleConstructorReturn(this, _Barcode.call(this, data.toUpperCase(), options));
	
			_this.text = _this.options.text || _this.text.replace(/[A-D]/g, '');
			return _this;
		}
	
		codabar.prototype.valid = function valid() {
			return this.data.search(/^[A-D][0-9\-\$\:\.\+\/]+[A-D]$/) !== -1;
		};
	
		codabar.prototype.encode = function encode() {
			var result = [];
			var encodings = this.getEncodings();
			for (var i = 0; i < this.data.length; i++) {
				result.push(encodings[this.data.charAt(i)]);
				// for all characters except the last, append a narrow-space ("0")
				if (i !== this.data.length - 1) {
					result.push("0");
				}
			}
			return {
				text: this.text,
				data: result.join('')
			};
		};
	
		codabar.prototype.getEncodings = function getEncodings() {
			return {
				"0": "101010011",
				"1": "101011001",
				"2": "101001011",
				"3": "110010101",
				"4": "101101001",
				"5": "110101001",
				"6": "100101011",
				"7": "100101101",
				"8": "100110101",
				"9": "110100101",
				"-": "101001101",
				"$": "101100101",
				":": "1101011011",
				"/": "1101101011",
				".": "1101101101",
				"+": "101100110011",
				"A": "1011001001",
				"B": "1010010011",
				"C": "1001001011",
				"D": "1010011001"
			};
		};
	
		return codabar;
	}(_Barcode3.default);
	
	exports.codabar = codabar;

/***/ }),
/* 67 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	exports.GenericBarcode = undefined;
	
	var _Barcode2 = __webpack_require__(42);
	
	var _Barcode3 = _interopRequireDefault(_Barcode2);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }
	
	var GenericBarcode = function (_Barcode) {
		_inherits(GenericBarcode, _Barcode);
	
		function GenericBarcode(data, options) {
			_classCallCheck(this, GenericBarcode);
	
			return _possibleConstructorReturn(this, _Barcode.call(this, data, options)); // Sets this.data and this.text
		}
	
		// Return the corresponding binary numbers for the data provided
	
	
		GenericBarcode.prototype.encode = function encode() {
			return {
				data: "10101010101010101010101010101010101010101",
				text: this.text
			};
		};
	
		// Resturn true/false if the string provided is valid for this encoder
	
	
		GenericBarcode.prototype.valid = function valid() {
			return true;
		};
	
		return GenericBarcode;
	}(_Barcode3.default);
	
	exports.GenericBarcode = GenericBarcode;

/***/ }),
/* 68 */
/***/ (function(module, exports) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	exports.default = merge;
	
	
	function merge(old, replaceObj) {
		var newMerge = {};
		var k;
		for (k in old) {
			if (old.hasOwnProperty(k)) {
				newMerge[k] = old[k];
			}
		}
		for (k in replaceObj) {
			if (replaceObj.hasOwnProperty(k) && typeof replaceObj[k] !== "undefined") {
				newMerge[k] = replaceObj[k];
			}
		}
		return newMerge;
	}

/***/ }),
/* 69 */
/***/ (function(module, exports) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	exports.default = linearizeEncodings;
	
	// Encodings can be nestled like [[1-1, 1-2], 2, [3-1, 3-2]
	// Convert to [1-1, 1-2, 2, 3-1, 3-2]
	
	function linearizeEncodings(encodings) {
		var linearEncodings = [];
		function nextLevel(encoded) {
			if (Array.isArray(encoded)) {
				for (var i = 0; i < encoded.length; i++) {
					nextLevel(encoded[i]);
				}
			} else {
				encoded.text = encoded.text || "";
				encoded.data = encoded.data || "";
				linearEncodings.push(encoded);
			}
		}
		nextLevel(encodings);
	
		return linearEncodings;
	}

/***/ }),
/* 70 */
/***/ (function(module, exports) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	exports.default = fixOptions;
	
	
	function fixOptions(options) {
		// Fix the margins
		options.marginTop = options.marginTop || options.margin;
		options.marginBottom = options.marginBottom || options.margin;
		options.marginRight = options.marginRight || options.margin;
		options.marginLeft = options.marginLeft || options.margin;
	
		return options;
	}

/***/ }),
/* 71 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _getOptionsFromElement = __webpack_require__(72);
	
	var _getOptionsFromElement2 = _interopRequireDefault(_getOptionsFromElement);
	
	var _renderers = __webpack_require__(75);
	
	var _exceptions = __webpack_require__(79);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	// Takes an element and returns an object with information about how
	// it should be rendered
	// This could also return an array with these objects
	// {
	//   element: The element that the renderer should draw on
	//   renderer: The name of the renderer
	//   afterRender (optional): If something has to done after the renderer
	//     completed, calls afterRender (function)
	//   options (optional): Options that can be defined in the element
	// }
	
	function getRenderProperties(element) {
		// If the element is a string, query select call again
		if (typeof element === "string") {
			return querySelectedRenderProperties(element);
		}
		// If element is array. Recursivly call with every object in the array
		else if (Array.isArray(element)) {
				var returnArray = [];
				for (var i = 0; i < element.length; i++) {
					returnArray.push(getRenderProperties(element[i]));
				}
				return returnArray;
			}
			// If element, render on canvas and set the uri as src
			else if (typeof HTMLCanvasElement !== 'undefined' && element instanceof HTMLImageElement) {
					return newCanvasRenderProperties(element);
				}
				// If SVG
				else if (typeof SVGElement !== 'undefined' && element instanceof SVGElement) {
						return {
							element: element,
							options: (0, _getOptionsFromElement2.default)(element),
							renderer: (0, _renderers.getRendererClass)("svg")
						};
					}
					// If canvas (in browser)
					else if (typeof HTMLCanvasElement !== 'undefined' && element instanceof HTMLCanvasElement) {
							return {
								element: element,
								options: (0, _getOptionsFromElement2.default)(element),
								renderer: (0, _renderers.getRendererClass)("canvas")
							};
						}
						// If canvas (in node)
						else if (element.getContext) {
								return {
									element: element,
									renderer: (0, _renderers.getRendererClass)("canvas")
								};
							} else {
								throw new _exceptions.InvalidElementException();
							}
	} /* global HTMLImageElement */
	/* global HTMLCanvasElement */
	/* global SVGElement */
	
	function querySelectedRenderProperties(string) {
		var selector = document.querySelectorAll(string);
		if (selector.length === 0) {
			return undefined;
		} else {
			var returnArray = [];
			for (var i = 0; i < selector.length; i++) {
				returnArray.push(getRenderProperties(selector[i]));
			}
			return returnArray;
		}
	}
	
	function newCanvasRenderProperties(imgElement) {
		var canvas = document.createElement('canvas');
		return {
			element: canvas,
			options: (0, _getOptionsFromElement2.default)(imgElement),
			renderer: (0, _renderers.getRendererClass)("canvas"),
			afterRender: function afterRender() {
				imgElement.setAttribute("src", canvas.toDataURL());
			}
		};
	}
	
	exports.default = getRenderProperties;

/***/ }),
/* 72 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _optionsFromStrings = __webpack_require__(73);
	
	var _optionsFromStrings2 = _interopRequireDefault(_optionsFromStrings);
	
	var _defaults = __webpack_require__(74);
	
	var _defaults2 = _interopRequireDefault(_defaults);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function getOptionsFromElement(element) {
		var options = {};
		for (var property in _defaults2.default) {
			if (_defaults2.default.hasOwnProperty(property)) {
				// jsbarcode-*
				if (element.hasAttribute("jsbarcode-" + property.toLowerCase())) {
					options[property] = element.getAttribute("jsbarcode-" + property.toLowerCase());
				}
	
				// data-*
				if (element.hasAttribute("data-" + property.toLowerCase())) {
					options[property] = element.getAttribute("data-" + property.toLowerCase());
				}
			}
		}
	
		options["value"] = element.getAttribute("jsbarcode-value") || element.getAttribute("data-value");
	
		// Since all atributes are string they need to be converted to integers
		options = (0, _optionsFromStrings2.default)(options);
	
		return options;
	}
	
	exports.default = getOptionsFromElement;

/***/ }),
/* 73 */
/***/ (function(module, exports) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	exports.default = optionsFromStrings;
	
	// Convert string to integers/booleans where it should be
	
	function optionsFromStrings(options) {
		var intOptions = ["width", "height", "textMargin", "fontSize", "margin", "marginTop", "marginBottom", "marginLeft", "marginRight"];
	
		for (var intOption in intOptions) {
			if (intOptions.hasOwnProperty(intOption)) {
				intOption = intOptions[intOption];
				if (typeof options[intOption] === "string") {
					options[intOption] = parseInt(options[intOption], 10);
				}
			}
		}
	
		if (typeof options["displayValue"] === "string") {
			options["displayValue"] = options["displayValue"] != "false";
		}
	
		return options;
	}

/***/ }),
/* 74 */
/***/ (function(module, exports) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	var defaults = {
		width: 2,
		height: 100,
		format: "auto",
		displayValue: true,
		fontOptions: "",
		font: "monospace",
		text: undefined,
		textAlign: "center",
		textPosition: "bottom",
		textMargin: 2,
		fontSize: 20,
		background: "#ffffff",
		lineColor: "#000000",
		margin: 10,
		marginTop: undefined,
		marginBottom: undefined,
		marginLeft: undefined,
		marginRight: undefined,
		valid: function valid() {}
	};
	
	exports.default = defaults;

/***/ }),
/* 75 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	exports.getRendererClass = undefined;
	
	var _canvas = __webpack_require__(76);
	
	var _canvas2 = _interopRequireDefault(_canvas);
	
	var _svg = __webpack_require__(78);
	
	var _svg2 = _interopRequireDefault(_svg);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function getRendererClass(name) {
		switch (name) {
			case "canvas":
				return _canvas2.default;
			case "svg":
				return _svg2.default;
			default:
				throw new Error("Invalid rederer");
		}
	}
	
	exports.getRendererClass = getRendererClass;

/***/ }),
/* 76 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _merge = __webpack_require__(68);
	
	var _merge2 = _interopRequireDefault(_merge);
	
	var _shared = __webpack_require__(77);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	var CanvasRenderer = function () {
		function CanvasRenderer(canvas, encodings, options) {
			_classCallCheck(this, CanvasRenderer);
	
			this.canvas = canvas;
			this.encodings = encodings;
			this.options = options;
		}
	
		CanvasRenderer.prototype.render = function render() {
			// Abort if the browser does not support HTML5 canvas
			if (!this.canvas.getContext) {
				throw new Error('The browser does not support canvas.');
			}
	
			this.prepareCanvas();
			for (var i = 0; i < this.encodings.length; i++) {
				var encodingOptions = (0, _merge2.default)(this.options, this.encodings[i].options);
	
				this.drawCanvasBarcode(encodingOptions, this.encodings[i]);
				this.drawCanvasText(encodingOptions, this.encodings[i]);
	
				this.moveCanvasDrawing(this.encodings[i]);
			}
	
			this.restoreCanvas();
		};
	
		CanvasRenderer.prototype.prepareCanvas = function prepareCanvas() {
			// Get the canvas context
			var ctx = this.canvas.getContext("2d");
	
			ctx.save();
	
			(0, _shared.calculateEncodingAttributes)(this.encodings, this.options, ctx);
			var totalWidth = (0, _shared.getTotalWidthOfEncodings)(this.encodings);
			var maxHeight = (0, _shared.getMaximumHeightOfEncodings)(this.encodings);
	
			this.canvas.width = totalWidth + this.options.marginLeft + this.options.marginRight;
	
			this.canvas.height = maxHeight;
	
			// Paint the canvas
			ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
			if (this.options.background) {
				ctx.fillStyle = this.options.background;
				ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
			}
	
			ctx.translate(this.options.marginLeft, 0);
		};
	
		CanvasRenderer.prototype.drawCanvasBarcode = function drawCanvasBarcode(options, encoding) {
			// Get the canvas context
			var ctx = this.canvas.getContext("2d");
	
			var binary = encoding.data;
	
			// Creates the barcode out of the encoded binary
			var yFrom;
			if (options.textPosition == "top") {
				yFrom = options.marginTop + options.fontSize + options.textMargin;
			} else {
				yFrom = options.marginTop;
			}
	
			ctx.fillStyle = options.lineColor;
	
			for (var b = 0; b < binary.length; b++) {
				var x = b * options.width + encoding.barcodePadding;
	
				if (binary[b] === "1") {
					ctx.fillRect(x, yFrom, options.width, options.height);
				} else if (binary[b]) {
					ctx.fillRect(x, yFrom, options.width, options.height * binary[b]);
				}
			}
		};
	
		CanvasRenderer.prototype.drawCanvasText = function drawCanvasText(options, encoding) {
			// Get the canvas context
			var ctx = this.canvas.getContext("2d");
	
			var font = options.fontOptions + " " + options.fontSize + "px " + options.font;
	
			// Draw the text if displayValue is set
			if (options.displayValue) {
				var x, y;
	
				if (options.textPosition == "top") {
					y = options.marginTop + options.fontSize - options.textMargin;
				} else {
					y = options.height + options.textMargin + options.marginTop + options.fontSize;
				}
	
				ctx.font = font;
	
				// Draw the text in the correct X depending on the textAlign option
				if (options.textAlign == "left" || encoding.barcodePadding > 0) {
					x = 0;
					ctx.textAlign = 'left';
				} else if (options.textAlign == "right") {
					x = encoding.width - 1;
					ctx.textAlign = 'right';
				}
				// In all other cases, center the text
				else {
						x = encoding.width / 2;
						ctx.textAlign = 'center';
					}
	
				ctx.fillText(encoding.text, x, y);
			}
		};
	
		CanvasRenderer.prototype.moveCanvasDrawing = function moveCanvasDrawing(encoding) {
			var ctx = this.canvas.getContext("2d");
	
			ctx.translate(encoding.width, 0);
		};
	
		CanvasRenderer.prototype.restoreCanvas = function restoreCanvas() {
			// Get the canvas context
			var ctx = this.canvas.getContext("2d");
	
			ctx.restore();
		};
	
		return CanvasRenderer;
	}();
	
	exports.default = CanvasRenderer;

/***/ }),
/* 77 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	exports.getTotalWidthOfEncodings = exports.calculateEncodingAttributes = exports.getBarcodePadding = exports.getEncodingHeight = exports.getMaximumHeightOfEncodings = undefined;
	
	var _merge = __webpack_require__(68);
	
	var _merge2 = _interopRequireDefault(_merge);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function getEncodingHeight(encoding, options) {
		return options.height + (options.displayValue && encoding.text.length > 0 ? options.fontSize + options.textMargin : 0) + options.marginTop + options.marginBottom;
	}
	
	function getBarcodePadding(textWidth, barcodeWidth, options) {
		if (options.displayValue && barcodeWidth < textWidth) {
			if (options.textAlign == "center") {
				return Math.floor((textWidth - barcodeWidth) / 2);
			} else if (options.textAlign == "left") {
				return 0;
			} else if (options.textAlign == "right") {
				return Math.floor(textWidth - barcodeWidth);
			}
		}
		return 0;
	}
	
	function calculateEncodingAttributes(encodings, barcodeOptions, context) {
		for (var i = 0; i < encodings.length; i++) {
			var encoding = encodings[i];
			var options = (0, _merge2.default)(barcodeOptions, encoding.options);
	
			// Calculate the width of the encoding
			var textWidth = messureText(encoding.text, options, context);
			var barcodeWidth = encoding.data.length * options.width;
			encoding.width = Math.ceil(Math.max(textWidth, barcodeWidth));
	
			encoding.height = getEncodingHeight(encoding, options);
	
			encoding.barcodePadding = getBarcodePadding(textWidth, barcodeWidth, options);
		}
	}
	
	function getTotalWidthOfEncodings(encodings) {
		var totalWidth = 0;
		for (var i = 0; i < encodings.length; i++) {
			totalWidth += encodings[i].width;
		}
		return totalWidth;
	}
	
	function getMaximumHeightOfEncodings(encodings) {
		var maxHeight = 0;
		for (var i = 0; i < encodings.length; i++) {
			if (encodings[i].height > maxHeight) {
				maxHeight = encodings[i].height;
			}
		}
		return maxHeight;
	}
	
	function messureText(string, options, context) {
		var ctx;
		if (typeof context === "undefined") {
			ctx = document.createElement("canvas").getContext("2d");
		} else {
			ctx = context;
		}
	
		ctx.font = options.fontOptions + " " + options.fontSize + "px " + options.font;
	
		// Calculate the width of the encoding
		var size = ctx.measureText(string).width;
	
		return size;
	}
	
	exports.getMaximumHeightOfEncodings = getMaximumHeightOfEncodings;
	exports.getEncodingHeight = getEncodingHeight;
	exports.getBarcodePadding = getBarcodePadding;
	exports.calculateEncodingAttributes = calculateEncodingAttributes;
	exports.getTotalWidthOfEncodings = getTotalWidthOfEncodings;

/***/ }),
/* 78 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _merge = __webpack_require__(68);
	
	var _merge2 = _interopRequireDefault(_merge);
	
	var _shared = __webpack_require__(77);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	var svgns = "http://www.w3.org/2000/svg";
	
	var SVGRenderer = function () {
		function SVGRenderer(svg, encodings, options) {
			_classCallCheck(this, SVGRenderer);
	
			this.svg = svg;
			this.encodings = encodings;
			this.options = options;
		}
	
		SVGRenderer.prototype.render = function render() {
			var currentX = this.options.marginLeft;
	
			this.prepareSVG();
			for (var i = 0; i < this.encodings.length; i++) {
				var encoding = this.encodings[i];
				var encodingOptions = (0, _merge2.default)(this.options, encoding.options);
	
				var group = createGroup(currentX, encodingOptions.marginTop, this.svg);
	
				setGroupOptions(group, encodingOptions);
	
				this.drawSvgBarcode(group, encodingOptions, encoding);
				this.drawSVGText(group, encodingOptions, encoding);
	
				currentX += encoding.width;
			}
		};
	
		SVGRenderer.prototype.prepareSVG = function prepareSVG() {
			// Clear the SVG
			while (this.svg.firstChild) {
				this.svg.removeChild(this.svg.firstChild);
			}
	
			(0, _shared.calculateEncodingAttributes)(this.encodings, this.options);
			var totalWidth = (0, _shared.getTotalWidthOfEncodings)(this.encodings);
			var maxHeight = (0, _shared.getMaximumHeightOfEncodings)(this.encodings);
	
			var width = totalWidth + this.options.marginLeft + this.options.marginRight;
			this.setSvgAttributes(width, maxHeight);
	
			if (this.options.background) {
				drawRect(0, 0, width, maxHeight, this.svg).setAttribute("style", "fill:" + this.options.background + ";");
			}
		};
	
		SVGRenderer.prototype.drawSvgBarcode = function drawSvgBarcode(parent, options, encoding) {
			var binary = encoding.data;
	
			// Creates the barcode out of the encoded binary
			var yFrom;
			if (options.textPosition == "top") {
				yFrom = options.fontSize + options.textMargin;
			} else {
				yFrom = 0;
			}
	
			var barWidth = 0;
			var x = 0;
			for (var b = 0; b < binary.length; b++) {
				x = b * options.width + encoding.barcodePadding;
	
				if (binary[b] === "1") {
					barWidth++;
				} else if (barWidth > 0) {
					drawRect(x - options.width * barWidth, yFrom, options.width * barWidth, options.height, parent);
					barWidth = 0;
				}
			}
	
			// Last draw is needed since the barcode ends with 1
			if (barWidth > 0) {
				drawRect(x - options.width * (barWidth - 1), yFrom, options.width * barWidth, options.height, parent);
			}
		};
	
		SVGRenderer.prototype.drawSVGText = function drawSVGText(parent, options, encoding) {
			var textElem = document.createElementNS(svgns, 'text');
	
			// Draw the text if displayValue is set
			if (options.displayValue) {
				var x, y;
	
				textElem.setAttribute("style", "font:" + options.fontOptions + " " + options.fontSize + "px " + options.font);
	
				if (options.textPosition == "top") {
					y = options.fontSize - options.textMargin;
				} else {
					y = options.height + options.textMargin + options.fontSize;
				}
	
				// Draw the text in the correct X depending on the textAlign option
				if (options.textAlign == "left" || encoding.barcodePadding > 0) {
					x = 0;
					textElem.setAttribute("text-anchor", "start");
				} else if (options.textAlign == "right") {
					x = encoding.width - 1;
					textElem.setAttribute("text-anchor", "end");
				}
				// In all other cases, center the text
				else {
						x = encoding.width / 2;
						textElem.setAttribute("text-anchor", "middle");
					}
	
				textElem.setAttribute("x", x);
				textElem.setAttribute("y", y);
	
				textElem.appendChild(document.createTextNode(encoding.text));
	
				parent.appendChild(textElem);
			}
		};
	
		SVGRenderer.prototype.setSvgAttributes = function setSvgAttributes(width, height) {
			var svg = this.svg;
			svg.setAttribute("width", width + "px");
			svg.setAttribute("height", height + "px");
			svg.setAttribute("x", "0px");
			svg.setAttribute("y", "0px");
			svg.setAttribute("viewBox", "0 0 " + width + " " + height);
	
			svg.setAttribute("xmlns", svgns);
			svg.setAttribute("version", "1.1");
	
			svg.style.transform = "translate(0,0)";
		};
	
		return SVGRenderer;
	}();
	
	function createGroup(x, y, parent) {
		var group = document.createElementNS(svgns, 'g');
	
		group.setAttribute("transform", "translate(" + x + ", " + y + ")");
	
		parent.appendChild(group);
	
		return group;
	}
	
	function setGroupOptions(group, options) {
		group.setAttribute("style", "fill:" + options.lineColor + ";");
	}
	
	function drawRect(x, y, width, height, parent) {
		var rect = document.createElementNS(svgns, 'rect');
	
		rect.setAttribute("x", x);
		rect.setAttribute("y", y);
		rect.setAttribute("width", width);
		rect.setAttribute("height", height);
	
		parent.appendChild(rect);
	
		return rect;
	}
	
	exports.default = SVGRenderer;

/***/ }),
/* 79 */
/***/ (function(module, exports) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }
	
	function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }
	
	var InvalidInputException = function (_Error) {
		_inherits(InvalidInputException, _Error);
	
		function InvalidInputException(symbology, input) {
			_classCallCheck(this, InvalidInputException);
	
			var _this = _possibleConstructorReturn(this, _Error.call(this));
	
			_this.name = "InvalidInputException";
	
			_this.symbology = symbology;
			_this.input = input;
	
			_this.message = '"' + _this.input + '" is not a valid input for ' + _this.symbology;
			return _this;
		}
	
		return InvalidInputException;
	}(Error);
	
	var InvalidElementException = function (_Error2) {
		_inherits(InvalidElementException, _Error2);
	
		function InvalidElementException() {
			_classCallCheck(this, InvalidElementException);
	
			var _this2 = _possibleConstructorReturn(this, _Error2.call(this));
	
			_this2.name = "InvalidElementException";
			_this2.message = "Not supported type to render on";
			return _this2;
		}
	
		return InvalidElementException;
	}(Error);
	
	var NoElementException = function (_Error3) {
		_inherits(NoElementException, _Error3);
	
		function NoElementException() {
			_classCallCheck(this, NoElementException);
	
			var _this3 = _possibleConstructorReturn(this, _Error3.call(this));
	
			_this3.name = "NoElementException";
			_this3.message = "No element to render on.";
			return _this3;
		}
	
		return NoElementException;
	}(Error);
	
	exports.InvalidInputException = InvalidInputException;
	exports.InvalidElementException = InvalidElementException;
	exports.NoElementException = NoElementException;

/***/ }),
/* 80 */
/***/ (function(module, exports) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }
	
	/*eslint no-console: 0 */
	
	var ErrorHandler = function () {
		function ErrorHandler(api) {
			_classCallCheck(this, ErrorHandler);
	
			this.api = api;
		}
	
		ErrorHandler.prototype.handleCatch = function handleCatch(e) {
			// If babel supported extending of Error in a correct way instanceof would be used here
			if (e.name === "InvalidInputException") {
				if (this.api._options.valid !== this.api._defaults.valid) {
					this.api._options.valid(false);
				} else {
					throw e.message;
				}
			} else {
				throw e;
			}
	
			this.api.render = function () {};
		};
	
		ErrorHandler.prototype.wrapBarcodeCall = function wrapBarcodeCall(func) {
			try {
				var result = func.apply(undefined, arguments);
				this.api._options.valid(true);
				return result;
			} catch (e) {
				this.handleCatch(e);
	
				return this.api;
			}
		};
	
		return ErrorHandler;
	}();
	
	exports.default = ErrorHandler;

/***/ }),
/* 81 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var DrawElement_1 = __webpack_require__(23);
	var Index_1 = __webpack_require__(16);
	var ControlHandle_1 = __webpack_require__(30);
	/**
	 * 使用CSS DOM 绘制矩形元素
	 */
	var DOMQrcodeDraw = (function () {
	    function DOMQrcodeDraw(qrcodeDrawElement) {
	        this.status = "";
	        this.QRErrorCorrectLevel = {
	            L: 1,
	            M: 0,
	            Q: 3,
	            H: 2
	        };
	        this.options = {
	            width: 128,
	            height: 128,
	            typeNumber: -1,
	            correctLevel: this.QRErrorCorrectLevel.M,
	            background: "#ffffff",
	            foreground: "#000000"
	        };
	        //qrcode的type类型大小写映射集合
	        this.qrcodeTypeListMap = {
	            'qrcode': 'qrcode',
	            'pdf417': 'pdf417',
	            'maxicode': 'MaxiCode',
	            'datamatrix': 'Data Matrix'
	        };
	        this.renderNode = Index_1.DOMUtils.CreateElement('div');
	        this.qrcodeDrawElement = qrcodeDrawElement;
	        this.updateQrcode();
	    }
	    DOMQrcodeDraw.prototype.getDrawContext = function () {
	        return this.renderNode;
	    };
	    DOMQrcodeDraw.prototype.isActive = function (args) {
	        var targetDom = args.event.target;
	        //事件不支持冒泡，这里判断一下中间的图片是否被选中
	        if (targetDom.parentNode == this.renderNode) {
	            targetDom = args.event.target.parentNode;
	        }
	        var ret = targetDom == this.renderNode;
	        return ret;
	    };
	    DOMQrcodeDraw.prototype.remove = function () {
	        this.status = "removed";
	        if (this.renderNode && this.renderNode.parentNode) {
	            this.renderNode.parentNode.removeChild(this.renderNode);
	        }
	    };
	    DOMQrcodeDraw.prototype.draw = function (qrcodeDrawElement) {
	        var div = this.renderNode;
	        var parent = qrcodeDrawElement.parent;
	        var left = qrcodeDrawElement.left, top = qrcodeDrawElement.top, width = qrcodeDrawElement.width, height = qrcodeDrawElement.height, alpha = qrcodeDrawElement.alpha, zIndex = qrcodeDrawElement.zIndex;
	        div.className = 'element-qrcode';
	        Index_1.DOMUtils.ApplyStyle(div, {
	            position: 'absolute',
	            left: Index_1.Unit.toPixel(left) + 'px',
	            top: Index_1.Unit.toPixel(top) + 'px',
	            width: Index_1.Unit.toPixel(width) + 'px',
	            height: Index_1.Unit.toPixel(height) + 'px',
	            boxSizing: 'border-box',
	            opacity: alpha,
	            zIndex: zIndex
	        });
	        if (this.status == "" || this.status == "removed") {
	            this.status = 'render';
	            var context_1 = parent.getDrawObject().getDrawContext();
	            context_1.appendChild(this.renderNode);
	        }
	    };
	    DOMQrcodeDraw.prototype.createCanvas = function (qrcode) {
	        var canvas = document.createElement('canvas');
	        canvas.width = this.options.width;
	        canvas.height = this.options.height;
	        var ctx = canvas.getContext('2d');
	        var tileW = this.options.width / qrcode.getModuleCount();
	        var tileH = this.options.height / qrcode.getModuleCount();
	        for (var row = 0; row < qrcode.getModuleCount(); row++) {
	            for (var col = 0; col < qrcode.getModuleCount(); col++) {
	                ctx.fillStyle = qrcode.isDark(row, col) ? this.options.foreground : this.options.background;
	                var w = (Math.ceil((col + 1) * tileW) - Math.floor(col * tileW));
	                var h = (Math.ceil((row + 1) * tileW) - Math.floor(row * tileW));
	                ctx.fillRect(Math.round(col * tileW), Math.round(row * tileH), w, h);
	            }
	        }
	        return canvas;
	    };
	    DOMQrcodeDraw.prototype.updateQrcode = function () {
	        var qrcodeDrawElement = this.qrcodeDrawElement;
	        var qrcode = new QRCode(this.options.typeNumber, this.options.correctLevel);
	        qrcode.addData(qrcodeDrawElement.url);
	        qrcode.make();
	        this.renderNode.innerHTML = "";
	        this.renderNode.appendChild(this.createCanvas(qrcode));
	        // var qrcodeDrawElement = this.qrcodeDrawElement;
	        // var qr = qrcode(4, "L");
	        // qr.addData(qrcodeDrawElement.url);
	        // qr.make();
	        // this.renderNode.innerHTML = "";
	        // this.renderNode.innerHTML = qr.createImgTag(4,1);
	    };
	    return DOMQrcodeDraw;
	}());
	/**
	 * 矩形元素
	 */
	var QrcodeDrawElement = (function (_super) {
	    __extends(QrcodeDrawElement, _super);
	    function QrcodeDrawElement(url, left, top, width, height, page) {
	        if (left === void 0) { left = 10; }
	        if (top === void 0) { top = 10; }
	        var _this = _super.call(this) || this;
	        _this.defaultUrl = "http://www.cainiao.com";
	        _this.url = "http://www.cainiao.com";
	        _this.type = "qrcode";
	        _this.alpha = 1;
	        _this.zIndex = 0;
	        _this.width = 30;
	        _this.height = 30;
	        _this.configable = true;
	        _this.ratioMode = "keepRatio";
	        if (url)
	            _this.url = url;
	        _this.left = left ? left : 10;
	        _this.top = top ? left : 10;
	        _this.pagesize = page ? { width: page.width, height: page.height } : { width: 100, height: 180 };
	        var limitSize;
	        if (width && height) {
	            _this.width = width;
	            _this.height = height;
	        }
	        limitSize = _this.getLimitSize(_this.pagesize, {
	            width: _this.width,
	            height: _this.height
	        });
	        _this.width = limitSize.width;
	        _this.height = limitSize.height;
	        _this.drawObject = new DOMQrcodeDraw(_this);
	        _this.control = new ControlHandle_1.DOMControlHandle(_this);
	        return _this;
	    }
	    QrcodeDrawElement.prototype.getDrawObject = function () {
	        return this.drawObject;
	    };
	    QrcodeDrawElement.prototype.getTemplate = function () {
	        var style = "opacity:" + this.alpha + ";";
	        var optionProp_primary, optionProp_schema;
	        optionProp_primary = this.primary ? "primary=\"" + this.primary + "\"" : "";
	        optionProp_schema = this.schema ? "schema=\"" + this.schema + "\"" : "";
	        var errorCorrection = this.errorCorrection ? "errorCorrection=\"" + this.errorCorrection + "\"" : '';
	        var symbolSize = this.symbolSize ? "symbolSize=\"" + this.symbolSize + "\"" : '';
	        var template = "<barcode type=\"" + this.type + "\" ratioMode=\"" + this.ratioMode + "\" " + errorCorrection + " " + symbolSize + "  style=\"" + style + "\" " + optionProp_primary + " " + optionProp_schema + "><![CDATA[" + this.url + "]]></barcode>";
	        return template;
	    };
	    /**
	     *  图片宽高的判断
	     */
	    QrcodeDrawElement.prototype.getLimitSize = function (pagesize, imgSize) {
	        //图片宽高比例
	        var iWhRate = imgSize.width / imgSize.height;
	        //画布宽高
	        var pageSize = pagesize;
	        //画布宽高比例
	        var pWhRate = pageSize.width / pageSize.height;
	        //最终限制宽高
	        var limitSize = imgSize;
	        if (iWhRate >= pWhRate) {
	            //图片宽度更大，限制大小以图片宽度为参考
	            if (imgSize.width >= pageSize.width) {
	                limitSize.width = pageSize.width;
	                limitSize.height = pageSize.width / iWhRate;
	            }
	        }
	        else {
	            //图片高度更大，限制大小以图片高度为参考
	            if (imgSize.height >= pageSize.height) {
	                limitSize.height = pageSize.height;
	                limitSize.width = pageSize.height * iWhRate;
	            }
	        }
	        limitSize.width = limitSize.width > 0 ? limitSize.width : 50;
	        limitSize.height = limitSize.height > 0 ? limitSize.height : 50;
	        return limitSize;
	    };
	    QrcodeDrawElement.prototype.clone = function () {
	        var offset = 10;
	        var cloned = _super.prototype.clone.call(this);
	        cloned.left = parseFloat(cloned.left) + offset;
	        cloned.top = parseFloat(cloned.top) + offset;
	        cloned.drawObject = new DOMQrcodeDraw(cloned);
	        cloned.control = new ControlHandle_1.DOMControlHandle(cloned);
	        return cloned;
	    };
	    return QrcodeDrawElement;
	}(DrawElement_1.DrawElement));
	exports.QrcodeDrawElement = QrcodeDrawElement;


/***/ }),
/* 82 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var DrawElement_1 = __webpack_require__(23);
	var Index_1 = __webpack_require__(16);
	/**
	 * 使用CSS DOM 绘制Layout元素
	 */
	var DOMScriptDraw = (function () {
	    function DOMScriptDraw() {
	        this.status = "";
	        this.renderNode = Index_1.DOMUtils.CreateElement('span');
	        this.renderNode.className = 'hidden';
	    }
	    DOMScriptDraw.prototype.getDrawContext = function () {
	        return this.renderNode;
	    };
	    DOMScriptDraw.prototype.isActive = function () {
	        return false;
	    };
	    DOMScriptDraw.prototype.remove = function () {
	        this.status = "removed";
	        if (this.renderNode && this.renderNode.parentNode) {
	            this.renderNode.parentNode.removeChild(this.renderNode);
	        }
	    };
	    DOMScriptDraw.prototype.draw = function (layoutEelement) {
	        var div = this.renderNode;
	        var parent = layoutEelement.parent;
	        div.className = 'element-script';
	        Index_1.DOMUtils.ApplyStyle(div, {
	            left: Index_1.Unit.toPixel(layoutEelement.left) + 'px',
	            top: Index_1.Unit.toPixel(layoutEelement.top) + 'px'
	        });
	        if (this.status == "" || this.status == "removed") {
	            this.status = 'render';
	            var context_1 = parent.getDrawObject().getDrawContext();
	            context_1.appendChild(this.renderNode);
	        }
	    };
	    return DOMScriptDraw;
	}());
	/**
	 * Script元素
	 */
	var ScriptDrawElement = (function (_super) {
	    __extends(ScriptDrawElement, _super);
	    function ScriptDrawElement(text) {
	        var _this = _super.call(this) || this;
	        _this.text = text;
	        _this.drawObject = new DOMScriptDraw();
	        return _this;
	    }
	    ScriptDrawElement.prototype.getDrawObject = function () {
	        return this.drawObject;
	    };
	    ScriptDrawElement.prototype.render = function () {
	        this.getDrawObject().draw(this);
	    };
	    ScriptDrawElement.prototype.getTemplate = function () {
	        var template = "";
	        var childrenTemplate = [];
	        for (var _i = 0, _a = this.children; _i < _a.length; _i++) {
	            var child = _a[_i];
	            var childElement = child;
	            childrenTemplate.push(childElement.getTemplate());
	        }
	        template = this.text + "\n";
	        return template;
	    };
	    ScriptDrawElement.prototype.isActive = function (args) {
	        return false;
	    };
	    return ScriptDrawElement;
	}(DrawElement_1.DrawElement));
	exports.ScriptDrawElement = ScriptDrawElement;


/***/ }),
/* 83 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var DrawElement_1 = __webpack_require__(23);
	var Index_1 = __webpack_require__(16);
	var ControlHandle_1 = __webpack_require__(30);
	/**
	 * 使用CSS DOM 绘制文本元素
	 */
	var DOMComponentDraw = (function (_super) {
	    __extends(DOMComponentDraw, _super);
	    function DOMComponentDraw() {
	        var _this = _super.call(this) || this;
	        _this.status = "";
	        _this.renderNode = Index_1.DOMUtils.CreateElement('div', { className: 'element-component' });
	        _this.renderNode.innerHTML = '<div class="placeholder">请选择打印项</div>';
	        return _this;
	    }
	    DOMComponentDraw.prototype.isActive = function (args) {
	        var ret = this.renderNode.contains(args.event.target);
	        return ret;
	    };
	    DOMComponentDraw.prototype.draw = function (component) {
	        var div = this.renderNode;
	        var parent = component.parent;
	        var context = parent.getDrawObject().getDrawContext();
	        var left = component.left, top = component.top, width = component.width, height = component.height;
	        left = Index_1.Unit.toPixel(left);
	        top = Index_1.Unit.toPixel(top);
	        width = Index_1.Unit.toPixel(width);
	        height = Index_1.Unit.toPixel(height);
	        Index_1.DOMUtils.ApplyStyle(div, {
	            left: left + 'px',
	            height: height + 'px',
	            width: width + 'px',
	            top: top + 'px',
	        });
	        if (this.status == "" || this.status == "removed") {
	            this.status = 'render';
	            context.appendChild(this.renderNode);
	        }
	    };
	    return DOMComponentDraw;
	}(DrawElement_1.DrawObject));
	/**
	 * Component元素
	 */
	var ComponentDrawElement = (function (_super) {
	    __extends(ComponentDrawElement, _super);
	    function ComponentDrawElement() {
	        var _this = _super.call(this) || this;
	        _this.left = 5;
	        _this.top = 5;
	        _this.width = 43.13;
	        _this.height = 7.94;
	        _this.drawObject = new DOMComponentDraw();
	        _this.control = new ControlHandle_1.DOMControlHandle(_this);
	        return _this;
	    }
	    ComponentDrawElement.prototype.getDrawObject = function () {
	        return this.drawObject;
	    };
	    ComponentDrawElement.prototype.render = function () {
	        this.getDrawObject().draw(this);
	    };
	    ComponentDrawElement.prototype.getTemplate = function () {
	        return "<layout editor:component=\"true\"></layout>";
	    };
	    return ComponentDrawElement;
	}(DrawElement_1.DrawElement));
	exports.ComponentDrawElement = ComponentDrawElement;
	//  var iterator = this.iterator;


/***/ }),
/* 84 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var DrawElement_1 = __webpack_require__(23);
	var Index_1 = __webpack_require__(16);
	/**
	 * 使用CSS DOM 绘制Layout元素
	 */
	var DOMLayoutDraw = (function (_super) {
	    __extends(DOMLayoutDraw, _super);
	    function DOMLayoutDraw() {
	        var _this = _super.call(this) || this;
	        _this.status = "";
	        _this.renderNode = Index_1.DOMUtils.CreateElement('div');
	        return _this;
	    }
	    DOMLayoutDraw.prototype.isActive = function () {
	        return false;
	    };
	    DOMLayoutDraw.prototype.draw = function (layoutEelement) {
	        var div = this.renderNode;
	        var parent = layoutEelement.parent;
	        var parentContext = parent.getDrawObject().getDrawContext();
	        var left = layoutEelement.left, top = layoutEelement.top, width = layoutEelement.width, height = layoutEelement.height;
	        div.className = 'element-layout';
	        left = 0;
	        top = 0;
	        width = Index_1.Unit.toPixel(width);
	        height = Index_1.Unit.toPixel(height);
	        if (this.status == "" || this.status == "removed") {
	            this.status = 'render';
	            parentContext.appendChild(this.renderNode);
	        }
	    };
	    return DOMLayoutDraw;
	}(DrawElement_1.DrawObject));
	/**
	 * Header元素
	 */
	var HeaderDrawElement = (function (_super) {
	    __extends(HeaderDrawElement, _super);
	    function HeaderDrawElement(width, height) {
	        var _this = _super.call(this) || this;
	        _this.left = 0;
	        _this.top = 0;
	        _this.width = width;
	        _this.height = height;
	        _this.drawObject = new DOMLayoutDraw();
	        return _this;
	    }
	    HeaderDrawElement.prototype.getDrawObject = function () {
	        return this.drawObject;
	    };
	    HeaderDrawElement.prototype.render = function () {
	        this.getDrawObject().draw(this);
	    };
	    HeaderDrawElement.prototype.getMetadata = function (key) {
	        var metadata = {
	            left: {
	                defaultValue: 0,
	                value: this.left,
	                type: Number
	            },
	            top: {
	                defaultValue: 0,
	                value: this.top,
	                type: Number
	            },
	            width: {
	                value: this.width,
	                type: Number
	            },
	            height: {
	                value: this.height,
	                type: Number
	            },
	            overflow: {
	                value: this.overflow
	            }
	        };
	        return metadata[key];
	    };
	    HeaderDrawElement.prototype.getComputedStyle = function (key) {
	        var propertyMetadata = this.getMetadata(key);
	        if (propertyMetadata.defaultValue == propertyMetadata.value) {
	            return '';
	        }
	        return key + ":" + propertyMetadata.value + ";";
	    };
	    HeaderDrawElement.prototype.getComputedProperty = function (key) {
	        var propertyMetadata = this.getMetadata(key);
	        if (propertyMetadata.defaultValue == propertyMetadata.value) {
	            return '';
	        }
	        return key + "=\"" + propertyMetadata.value + "\" ";
	    };
	    HeaderDrawElement.prototype.getTemplate = function () {
	        var template = "";
	        var childrenTemplate = [];
	        var props = [
	            this.getComputedProperty('width'),
	            this.getComputedProperty('height')
	        ];
	        var zIndex = this.zIndex ? "zIndex:" + this.zIndex + ";" : '';
	        var margin = this.margin ? "margin:" + this.margin + ";" : '';
	        var padding = this.padding ? "padding:" + this.padding + ";" : '';
	        var styles = [this.getComputedStyle('overflow')];
	        var style = "";
	        if (zIndex) {
	            styles.push(zIndex);
	        }
	        if (margin) {
	            styles.push(margin);
	        }
	        if (padding) {
	            styles.push(padding);
	        }
	        if (styles.join('').length) {
	            style = " style=\"" + styles.join('') + "\"";
	        }
	        for (var _i = 0, _a = this.children; _i < _a.length; _i++) {
	            var child = _a[_i];
	            var childElement = child;
	            childrenTemplate.push(childElement.getTemplate());
	        }
	        template = "<header " + props.join('') + style + ">" + childrenTemplate.join('') + "\n\t\t</header>";
	        return template;
	    };
	    HeaderDrawElement.prototype.isActive = function (args) {
	        return false;
	    };
	    return HeaderDrawElement;
	}(DrawElement_1.DrawElement));
	exports.HeaderDrawElement = HeaderDrawElement;


/***/ }),
/* 85 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var DrawElement_1 = __webpack_require__(23);
	var Index_1 = __webpack_require__(16);
	/**
	 * 使用CSS DOM 绘制Layout元素
	 */
	var DOMLayoutDraw = (function (_super) {
	    __extends(DOMLayoutDraw, _super);
	    function DOMLayoutDraw() {
	        var _this = _super.call(this) || this;
	        _this.status = "";
	        _this.renderNode = Index_1.DOMUtils.CreateElement('div');
	        return _this;
	    }
	    DOMLayoutDraw.prototype.isActive = function () {
	        return false;
	    };
	    DOMLayoutDraw.prototype.draw = function (layoutEelement) {
	        var div = this.renderNode;
	        var parent = layoutEelement.parent;
	        var parentContext = parent.getDrawObject().getDrawContext();
	        var left = layoutEelement.left, top = layoutEelement.top, width = layoutEelement.width, height = layoutEelement.height;
	        div.className = 'element-layout';
	        left = 0;
	        top = 0;
	        width = Index_1.Unit.toPixel(width);
	        height = Index_1.Unit.toPixel(height);
	        if (this.status == "" || this.status == "removed") {
	            this.status = 'render';
	            parentContext.appendChild(this.renderNode);
	        }
	    };
	    return DOMLayoutDraw;
	}(DrawElement_1.DrawObject));
	/**
	 * Header元素
	 */
	var FooterDrawElement = (function (_super) {
	    __extends(FooterDrawElement, _super);
	    function FooterDrawElement(width, height) {
	        var _this = _super.call(this) || this;
	        _this.left = 0;
	        _this.top = 0;
	        _this.width = width;
	        _this.height = height;
	        _this.drawObject = new DOMLayoutDraw();
	        return _this;
	    }
	    FooterDrawElement.prototype.getDrawObject = function () {
	        return this.drawObject;
	    };
	    FooterDrawElement.prototype.render = function () {
	        this.getDrawObject().draw(this);
	    };
	    FooterDrawElement.prototype.getMetadata = function (key) {
	        var metadata = {
	            left: {
	                defaultValue: 0,
	                value: this.left,
	                type: Number
	            },
	            top: {
	                defaultValue: 0,
	                value: this.top,
	                type: Number
	            },
	            width: {
	                value: this.width,
	                type: Number
	            },
	            height: {
	                value: this.height,
	                type: Number
	            },
	            overflow: {
	                value: this.overflow
	            }
	        };
	        return metadata[key];
	    };
	    FooterDrawElement.prototype.getComputedStyle = function (key) {
	        var propertyMetadata = this.getMetadata(key);
	        if (propertyMetadata.defaultValue == propertyMetadata.value) {
	            return '';
	        }
	        return key + ":" + propertyMetadata.value + ";";
	    };
	    FooterDrawElement.prototype.getComputedProperty = function (key) {
	        var propertyMetadata = this.getMetadata(key);
	        if (propertyMetadata.defaultValue == propertyMetadata.value) {
	            return '';
	        }
	        return key + "=\"" + propertyMetadata.value + "\" ";
	    };
	    FooterDrawElement.prototype.getTemplate = function () {
	        var template = "";
	        var childrenTemplate = [];
	        var props = [
	            this.getComputedProperty('width'),
	            this.getComputedProperty('height')
	        ];
	        var zIndex = this.zIndex ? "zIndex:" + this.zIndex + ";" : '';
	        var margin = this.margin ? "margin:" + this.margin + ";" : '';
	        var padding = this.padding ? "padding:" + this.padding + ";" : '';
	        var styles = [this.getComputedStyle('overflow')];
	        var style = "";
	        if (zIndex) {
	            styles.push(zIndex);
	        }
	        if (margin) {
	            styles.push(margin);
	        }
	        if (padding) {
	            styles.push(padding);
	        }
	        if (styles.length) {
	            style = " style=\"" + styles.join('') + "\"";
	        }
	        for (var _i = 0, _a = this.children; _i < _a.length; _i++) {
	            var child = _a[_i];
	            var childElement = child;
	            childrenTemplate.push(childElement.getTemplate());
	        }
	        template = "<footer " + props.join('') + style + ">" + childrenTemplate.join('') + "\n\t\t</footer>";
	        return template;
	    };
	    FooterDrawElement.prototype.isActive = function (args) {
	        return false;
	    };
	    return FooterDrawElement;
	}(DrawElement_1.DrawElement));
	exports.FooterDrawElement = FooterDrawElement;


/***/ }),
/* 86 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var DrawElement_1 = __webpack_require__(23);
	var Index_1 = __webpack_require__(16);
	/**
	 * 使用CSS DOM 绘制Layout元素
	 */
	var DOMPageIndexDraw = (function () {
	    function DOMPageIndexDraw() {
	        this.status = "";
	        this.renderNode = Index_1.DOMUtils.CreateElement('span', { className: 'hidden' });
	    }
	    DOMPageIndexDraw.prototype.getDrawContext = function () {
	        return this.renderNode;
	    };
	    DOMPageIndexDraw.prototype.isActive = function () {
	        return false;
	    };
	    DOMPageIndexDraw.prototype.remove = function () {
	        this.status = "removed";
	        if (this.renderNode && this.renderNode.parentNode) {
	            this.renderNode.parentNode.removeChild(this.renderNode);
	        }
	    };
	    DOMPageIndexDraw.prototype.draw = function (layoutEelement) {
	        var div = this.renderNode;
	        var parent = layoutEelement.parent;
	        div.className = 'element-page-index';
	        Index_1.DOMUtils.ApplyStyle(div, {
	            position: 'relative',
	            left: Index_1.Unit.toPixel(layoutEelement.left) + 'px',
	            top: Index_1.Unit.toPixel(layoutEelement.top) + 'px'
	        });
	        if (this.status == "" || this.status == "removed") {
	            this.status = 'render';
	            var context_1 = parent.getDrawObject().getDrawContext();
	            context_1.appendChild(this.renderNode);
	        }
	    };
	    return DOMPageIndexDraw;
	}());
	/**
	 * PageIndex 元素
	 */
	var PageIndexDrawElement = (function (_super) {
	    __extends(PageIndexDrawElement, _super);
	    function PageIndexDrawElement(width, height) {
	        var _this = _super.call(this) || this;
	        _this.format = 'currentPageNumber/totalPageNumber';
	        _this.fontSize = 8;
	        _this.fontFamily = "SimHei";
	        _this.letterSpacing = 0;
	        _this.scale = 1;
	        _this.fontColor = '#000';
	        _this.backgroundColor = '#fff';
	        _this.lineHeight = "";
	        _this.fontWeight = "normal"; //枚举值light(淡)、normal（正常）、bold（加粗）
	        _this.direction = "ltr";
	        _this.align = 'left';
	        _this.valign = 'top';
	        _this.fontUnderline = false;
	        _this.fontItalic = false;
	        _this.opacity = 1;
	        _this.drawObject = new DOMPageIndexDraw();
	        if (width !== undefined) {
	            _this.width = width;
	        }
	        if (height !== undefined) {
	            _this.height = height;
	        }
	        return _this;
	    }
	    Object.defineProperty(PageIndexDrawElement.prototype, "alpha", {
	        get: function () {
	            return this.opacity;
	        },
	        set: function (value) {
	            this.opacity = value;
	        },
	        enumerable: true,
	        configurable: true
	    });
	    PageIndexDrawElement.prototype.getDrawObject = function () {
	        return this.drawObject;
	    };
	    PageIndexDrawElement.prototype.render = function () {
	        this.getDrawObject().draw(this);
	    };
	    PageIndexDrawElement.prototype.getMetadata = function (key) {
	        var metadata = {
	            backgroundColor: {
	                defaultValue: "#fff",
	                value: this.backgroundColor,
	                type: String
	            },
	            fontColor: {
	                defaultValue: "#000",
	                value: this.fontColor,
	                type: String
	            },
	            letterSpacing: {
	                defaultValue: 0,
	                value: this.letterSpacing,
	                type: Number
	            },
	            lineHeight: {
	                defaultValue: "",
	                value: this.lineHeight,
	                type: String
	            },
	            fontSize: {
	                defaultValue: 8,
	                value: this.fontSize,
	                type: Number
	            },
	            fontItalic: {
	                defaultValue: false,
	                value: this.fontItalic,
	                type: Boolean
	            },
	            fontUnderline: {
	                defaultValue: false,
	                value: this.fontUnderline,
	                type: Boolean
	            },
	            fontWeight: {
	                defaultValue: "normal",
	                value: this.fontWeight,
	                type: String
	            },
	            align: {
	                defaultValue: "left",
	                value: this.align,
	                type: String
	            },
	            valign: {
	                defaultValue: "top",
	                value: this.valign,
	                type: String
	            },
	            alpha: {
	                defaultValue: 1,
	                value: this.alpha,
	                type: Number
	            }
	        };
	        return metadata[key];
	    };
	    PageIndexDrawElement.prototype.getComputedStyle = function (key) {
	        var propertyMetadata = this.getMetadata(key);
	        if (propertyMetadata.defaultValue == propertyMetadata.value) {
	            return '';
	        }
	        return key + ":" + propertyMetadata.value + ";";
	    };
	    PageIndexDrawElement.prototype.getTemplate = function () {
	        var styles = [
	            this.getComputedStyle("fontWeight"),
	            this.getComputedStyle("fontItalic"),
	            this.getComputedStyle("fontUnderline"),
	            this.getComputedStyle("fontSize"),
	            this.getComputedStyle("backgroundColor"),
	            this.getComputedStyle("fontColor"),
	            this.getComputedStyle('letterSpacing'),
	            this.getComputedStyle('lineHeight'),
	            this.getComputedStyle('align'),
	            this.getComputedStyle('valign'),
	            this.getComputedStyle('alpha')
	        ];
	        return "\n            <pageIndex format=\"" + this.format + "\" style=\"fontFamily:" + this.fontFamily + ";" + styles.join('') + "\"></pageIndex>\n        ";
	    };
	    PageIndexDrawElement.prototype.isActive = function (args) {
	        return false;
	    };
	    return PageIndexDrawElement;
	}(DrawElement_1.DrawElement));
	exports.PageIndexDrawElement = PageIndexDrawElement;


/***/ }),
/* 87 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	var Dispatcher_1 = __webpack_require__(4);
	var Index_1 = __webpack_require__(16);
	var Index_2 = __webpack_require__(26);
	__webpack_require__(88);
	// 画框
	//在空白区域，拖动鼠标
	// 判断元素是否在框内
	//如过元素在框内，添加选中元素
	var SelectionRect = (function () {
	    function SelectionRect(originX, originY, terminalX, terminalY) {
	        this.originX = originX;
	        this.originY = originY;
	        this.terminalX = terminalX;
	        this.terminalY = terminalY;
	    }
	    return SelectionRect;
	}());
	var SelectionState;
	(function (SelectionState) {
	    SelectionState[SelectionState["Origin"] = -1] = "Origin";
	    SelectionState[SelectionState["Start"] = 0] = "Start";
	    SelectionState[SelectionState["Selecting"] = 1] = "Selecting";
	})(SelectionState = exports.SelectionState || (exports.SelectionState = {}));
	;
	var MIN_SECTION_HEIGHT = 4;
	var MIN_SECTION_WIDTH = 4;
	var SelectionBox = (function () {
	    function SelectionBox() {
	        this.valid = false;
	        this.renderNode = Index_1.DOMUtils.CreateElement('div');
	        this.renderNode.className = 'designer-selectionbox';
	        Dispatcher_1.EventManager.register("DESINGER_SELECTION_STRAT_EVENT", this.onSelectionStart.bind(this));
	        Dispatcher_1.EventManager.register("DESINGER_SELECTION_MOVE_EVENT", this.onSelectionMove.bind(this));
	        Dispatcher_1.EventManager.register("DESINGER_SELECTION_END_EVENT", this.onSelectionEnd.bind(this));
	    }
	    SelectionBox.prototype.onSelectionMove = function (args) {
	        this.selectionRect.terminalX = args.clientX;
	        this.selectionRect.terminalY = args.clientY;
	        this.draw();
	    };
	    SelectionBox.prototype.onSelectionStart = function (args) {
	        this.selectionRect = new SelectionRect(args.clientX, args.clientY, args.clientX, args.clientY);
	    };
	    SelectionBox.prototype.onSelectionEnd = function (args) {
	        var target = args.event.target;
	        var iterator = target.page.iterator;
	        var selectionRect = this.renderNode.getBoundingClientRect();
	        this.valid = this.isValid();
	        if (!this.valid) {
	            this.hide();
	            return;
	        }
	        // target.unhandleClick();
	        var isSelected = false;
	        while (!iterator.next().done) {
	            var current = iterator.current;
	            var targetRect = current.getDrawObject().getDrawContext().getBoundingClientRect();
	            var b = Math.min(targetRect.bottom, selectionRect.top + selectionRect.height);
	            var t = Math.max(targetRect.top, selectionRect.top);
	            var l = Math.max(targetRect.left, selectionRect.left);
	            var r = Math.min(targetRect.right, selectionRect.left + selectionRect.width);
	            if (current instanceof Index_2.PageDrawElement || current.id == "CUSTOM_AREA") {
	                continue;
	            }
	            var cur = current;
	            var ignore = false;
	            while (cur) {
	                if (cur instanceof Index_2.TableDrawElement) {
	                    ignore = true;
	                    break;
	                }
	                else {
	                    cur = cur.parent;
	                }
	            }
	            if (ignore) {
	                continue;
	            }
	            if ((b >= t) && (l <= r)) {
	                target.select(current);
	                target.fireSelectedEvent(current);
	            }
	        }
	        this.hide();
	    };
	    SelectionBox.prototype.isValid = function () {
	        var selectionRect = this.renderNode.getBoundingClientRect();
	        return selectionRect.height >= MIN_SECTION_HEIGHT || selectionRect.width >= MIN_SECTION_WIDTH;
	    };
	    SelectionBox.prototype.hide = function () {
	        Index_1.DOMUtils.ApplyStyle(this.renderNode, {
	            'display': 'none'
	        });
	    };
	    SelectionBox.prototype.draw = function () {
	        var left = Math.min(this.selectionRect.originX, this.selectionRect.terminalX) + 'px';
	        var top = Math.min(this.selectionRect.originY, this.selectionRect.terminalY) + 'px';
	        var width = Math.abs(this.selectionRect.originX - this.selectionRect.terminalX) + 'px';
	        var height = Math.abs(this.selectionRect.originY - this.selectionRect.terminalY) + 'px';
	        var display = 'block';
	        Index_1.DOMUtils.ApplyStyle(this.renderNode, {
	            left: left,
	            top: top,
	            width: width,
	            height: height,
	            display: display
	        });
	    };
	    return SelectionBox;
	}());
	exports.SelectionBox = SelectionBox;


/***/ }),
/* 88 */
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),
/* 89 */,
/* 90 */
/***/ (function(module, exports) {

	/*
		MIT License http://www.opensource.org/licenses/mit-license.php
		Author Tobias Koppers @sokra
	*/
	// css base code, injected by the css-loader
	module.exports = function() {
		var list = [];
	
		// return the list of modules as css string
		list.toString = function toString() {
			var result = [];
			for(var i = 0; i < this.length; i++) {
				var item = this[i];
				if(item[2]) {
					result.push("@media " + item[2] + "{" + item[1] + "}");
				} else {
					result.push(item[1]);
				}
			}
			return result.join("");
		};
	
		// import a list of modules into the list
		list.i = function(modules, mediaQuery) {
			if(typeof modules === "string")
				modules = [[null, modules, ""]];
			var alreadyImportedModules = {};
			for(var i = 0; i < this.length; i++) {
				var id = this[i][0];
				if(typeof id === "number")
					alreadyImportedModules[id] = true;
			}
			for(i = 0; i < modules.length; i++) {
				var item = modules[i];
				// skip already imported module
				// this implementation is not 100% perfect for weird media query combinations
				//  when a module is imported multiple times with different media queries.
				//  I hope this will never occur (Hey this way we have smaller bundles)
				if(typeof item[0] !== "number" || !alreadyImportedModules[item[0]]) {
					if(mediaQuery && !item[2]) {
						item[2] = mediaQuery;
					} else if(mediaQuery) {
						item[2] = "(" + item[2] + ") and (" + mediaQuery + ")";
					}
					list.push(item);
				}
			}
		};
		return list;
	};


/***/ }),
/* 91 */
/***/ (function(module, exports, __webpack_require__) {

	/*
		MIT License http://www.opensource.org/licenses/mit-license.php
		Author Tobias Koppers @sokra
	*/
	var stylesInDom = {},
		memoize = function(fn) {
			var memo;
			return function () {
				if (typeof memo === "undefined") memo = fn.apply(this, arguments);
				return memo;
			};
		},
		isOldIE = memoize(function() {
			return /msie [6-9]\b/.test(self.navigator.userAgent.toLowerCase());
		}),
		getHeadElement = memoize(function () {
			return document.head || document.getElementsByTagName("head")[0];
		}),
		singletonElement = null,
		singletonCounter = 0,
		styleElementsInsertedAtTop = [];
	
	module.exports = function(list, options) {
		if(false) {
			if(typeof document !== "object") throw new Error("The style-loader cannot be used in a non-browser environment");
		}
	
		options = options || {};
		// Force single-tag solution on IE6-9, which has a hard limit on the # of <style>
		// tags it will allow on a page
		if (typeof options.singleton === "undefined") options.singleton = isOldIE();
	
		// By default, add <style> tags to the bottom of <head>.
		if (typeof options.insertAt === "undefined") options.insertAt = "bottom";
	
		var styles = listToStyles(list);
		addStylesToDom(styles, options);
	
		return function update(newList) {
			var mayRemove = [];
			for(var i = 0; i < styles.length; i++) {
				var item = styles[i];
				var domStyle = stylesInDom[item.id];
				domStyle.refs--;
				mayRemove.push(domStyle);
			}
			if(newList) {
				var newStyles = listToStyles(newList);
				addStylesToDom(newStyles, options);
			}
			for(var i = 0; i < mayRemove.length; i++) {
				var domStyle = mayRemove[i];
				if(domStyle.refs === 0) {
					for(var j = 0; j < domStyle.parts.length; j++)
						domStyle.parts[j]();
					delete stylesInDom[domStyle.id];
				}
			}
		};
	}
	
	function addStylesToDom(styles, options) {
		for(var i = 0; i < styles.length; i++) {
			var item = styles[i];
			var domStyle = stylesInDom[item.id];
			if(domStyle) {
				domStyle.refs++;
				for(var j = 0; j < domStyle.parts.length; j++) {
					domStyle.parts[j](item.parts[j]);
				}
				for(; j < item.parts.length; j++) {
					domStyle.parts.push(addStyle(item.parts[j], options));
				}
			} else {
				var parts = [];
				for(var j = 0; j < item.parts.length; j++) {
					parts.push(addStyle(item.parts[j], options));
				}
				stylesInDom[item.id] = {id: item.id, refs: 1, parts: parts};
			}
		}
	}
	
	function listToStyles(list) {
		var styles = [];
		var newStyles = {};
		for(var i = 0; i < list.length; i++) {
			var item = list[i];
			var id = item[0];
			var css = item[1];
			var media = item[2];
			var sourceMap = item[3];
			var part = {css: css, media: media, sourceMap: sourceMap};
			if(!newStyles[id])
				styles.push(newStyles[id] = {id: id, parts: [part]});
			else
				newStyles[id].parts.push(part);
		}
		return styles;
	}
	
	function insertStyleElement(options, styleElement) {
		var head = getHeadElement();
		var lastStyleElementInsertedAtTop = styleElementsInsertedAtTop[styleElementsInsertedAtTop.length - 1];
		if (options.insertAt === "top") {
			if(!lastStyleElementInsertedAtTop) {
				head.insertBefore(styleElement, head.firstChild);
			} else if(lastStyleElementInsertedAtTop.nextSibling) {
				head.insertBefore(styleElement, lastStyleElementInsertedAtTop.nextSibling);
			} else {
				head.appendChild(styleElement);
			}
			styleElementsInsertedAtTop.push(styleElement);
		} else if (options.insertAt === "bottom") {
			head.appendChild(styleElement);
		} else {
			throw new Error("Invalid value for parameter 'insertAt'. Must be 'top' or 'bottom'.");
		}
	}
	
	function removeStyleElement(styleElement) {
		styleElement.parentNode.removeChild(styleElement);
		var idx = styleElementsInsertedAtTop.indexOf(styleElement);
		if(idx >= 0) {
			styleElementsInsertedAtTop.splice(idx, 1);
		}
	}
	
	function createStyleElement(options) {
		var styleElement = document.createElement("style");
		styleElement.type = "text/css";
		insertStyleElement(options, styleElement);
		return styleElement;
	}
	
	function createLinkElement(options) {
		var linkElement = document.createElement("link");
		linkElement.rel = "stylesheet";
		insertStyleElement(options, linkElement);
		return linkElement;
	}
	
	function addStyle(obj, options) {
		var styleElement, update, remove;
	
		if (options.singleton) {
			var styleIndex = singletonCounter++;
			styleElement = singletonElement || (singletonElement = createStyleElement(options));
			update = applyToSingletonTag.bind(null, styleElement, styleIndex, false);
			remove = applyToSingletonTag.bind(null, styleElement, styleIndex, true);
		} else if(obj.sourceMap &&
			typeof URL === "function" &&
			typeof URL.createObjectURL === "function" &&
			typeof URL.revokeObjectURL === "function" &&
			typeof Blob === "function" &&
			typeof btoa === "function") {
			styleElement = createLinkElement(options);
			update = updateLink.bind(null, styleElement);
			remove = function() {
				removeStyleElement(styleElement);
				if(styleElement.href)
					URL.revokeObjectURL(styleElement.href);
			};
		} else {
			styleElement = createStyleElement(options);
			update = applyToTag.bind(null, styleElement);
			remove = function() {
				removeStyleElement(styleElement);
			};
		}
	
		update(obj);
	
		return function updateStyle(newObj) {
			if(newObj) {
				if(newObj.css === obj.css && newObj.media === obj.media && newObj.sourceMap === obj.sourceMap)
					return;
				update(obj = newObj);
			} else {
				remove();
			}
		};
	}
	
	var replaceText = (function () {
		var textStore = [];
	
		return function (index, replacement) {
			textStore[index] = replacement;
			return textStore.filter(Boolean).join('\n');
		};
	})();
	
	function applyToSingletonTag(styleElement, index, remove, obj) {
		var css = remove ? "" : obj.css;
	
		if (styleElement.styleSheet) {
			styleElement.styleSheet.cssText = replaceText(index, css);
		} else {
			var cssNode = document.createTextNode(css);
			var childNodes = styleElement.childNodes;
			if (childNodes[index]) styleElement.removeChild(childNodes[index]);
			if (childNodes.length) {
				styleElement.insertBefore(cssNode, childNodes[index]);
			} else {
				styleElement.appendChild(cssNode);
			}
		}
	}
	
	function applyToTag(styleElement, obj) {
		var css = obj.css;
		var media = obj.media;
	
		if(media) {
			styleElement.setAttribute("media", media)
		}
	
		if(styleElement.styleSheet) {
			styleElement.styleSheet.cssText = css;
		} else {
			while(styleElement.firstChild) {
				styleElement.removeChild(styleElement.firstChild);
			}
			styleElement.appendChild(document.createTextNode(css));
		}
	}
	
	function updateLink(linkElement, obj) {
		var css = obj.css;
		var sourceMap = obj.sourceMap;
	
		if(sourceMap) {
			// http://stackoverflow.com/a/26603875
			css += "\n/*# sourceMappingURL=data:application/json;base64," + btoa(unescape(encodeURIComponent(JSON.stringify(sourceMap)))) + " */";
		}
	
		var blob = new Blob([css], { type: "text/css" });
	
		var oldSrc = linkElement.href;
	
		linkElement.href = URL.createObjectURL(blob);
	
		if(oldSrc)
			URL.revokeObjectURL(oldSrc);
	}


/***/ }),
/* 92 */
/***/ (function(module, exports) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	var Arrange;
	(function (Arrange) {
	    Arrange[Arrange["Front"] = 1] = "Front";
	    Arrange[Arrange["Back"] = 2] = "Back"; //置底
	})(Arrange = exports.Arrange || (exports.Arrange = {}));
	var EditMode;
	(function (EditMode) {
	    EditMode[EditMode["SourceCode"] = 0] = "SourceCode";
	    EditMode[EditMode["Designer"] = 1] = "Designer"; //设计器
	})(EditMode = exports.EditMode || (exports.EditMode = {}));
	/**
	 * 定义资源的类型枚举
	 * template 模版
	 * customArea 自定义区
	 * component 组件
	 */
	var ResourceType;
	(function (ResourceType) {
	    ResourceType[ResourceType["template"] = 1] = "template";
	    ResourceType[ResourceType["customArea"] = 2] = "customArea";
	    ResourceType[ResourceType["component"] = 3] = "component";
	})(ResourceType = exports.ResourceType || (exports.ResourceType = {}));
	;


/***/ }),
/* 93 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	var Parser = __webpack_require__(94);
	var drawElementConfig = __webpack_require__(96); //元素构造函数的配置信息
	// let element = require( "../DrawElement/HeaderDrawElement");
	var parser = Parser.parser;
	/**
	 * 获取绘制元素的代理方法
	 * @returns {Proxy} 代理对象
	 */
	function ElementProxy() {
	    var target = {}, handler = {
	        get: function (target, key, receiver) {
	            if (key in target) {
	                return target[key];
	            }
	            else {
	                if (key == 'tr') {
	                    key = 'tableRow';
	                }
	                else if (key == 'td' || key == 'th') {
	                    key = 'tableCell';
	                }
	                var elementClass = key.substr(0, 1).toLocaleUpperCase() + key.substr(1, key.length) + "DrawElement";
	                var element = __webpack_require__(97)("./" + elementClass);
	                target[key] = element[elementClass];
	                return element[elementClass];
	            }
	        },
	        set: function (target, key, value, receiver) {
	            if (key in target) {
	                return target[key];
	            }
	            else {
	                target[key] = value;
	                return value;
	            }
	        }
	    };
	    return new Proxy(target, handler);
	}
	var elementProxy = {
	    get: function (key) {
	        var target = this;
	        if (key in target) {
	            return target[key];
	        }
	        else {
	            if (key == 'tr') {
	                key = 'tableRow';
	            }
	            else if (key == 'td' || key == 'th') {
	                key = 'tableCell';
	            }
	            var elementClass = key.substr(0, 1).toLocaleUpperCase() + key.substr(1, key.length) + "DrawElement";
	            var element = __webpack_require__(97)("./" + elementClass);
	            target[key] = element[elementClass];
	            return element[elementClass];
	        }
	    },
	    set: function (key, value) {
	        var target = this;
	        if (key in target) {
	            return target[key];
	        }
	        else {
	            target[key] = value;
	            return value;
	        }
	    }
	};
	/**
	 * 模板解析主要方法，使用示例：
	 *
	 * let parser = new TemplateParser();
	 * let tree = parser.parse('<page width="100" height="500">' +
	 *          '<layout>' +
	 *          '<text style="fontSize:20;orientation:horizontal;fontUnderline:true">测试</text>' +
	 *          '</layout>' +
	 *          '</page>');
	 */
	var TemplateParser = (function () {
	    function TemplateParser() {
	        //barcode的type类型集合
	        this.barcodeTypeList = ['code128', 'code39', 'code93', 'upca', 'upce', 'ean8', 'ean13', 'itf14', 'c25inter'];
	        //qrcode的type类型集合
	        this.qrcodeTypeList = ['qrcode', 'pdf417', 'maxicode', 'datamatrix'];
	        this.elementProxy = elementProxy; //ElementProxy();
	    }
	    /**
	     * 根据节点类型,处理相应元素
	     * @param type {string} 节点类型
	     * @param child {any} 子节点
	     * @param config {any} 节点配置信息(prop)
	     * @returns {any} DrawElement的子类
	     */
	    TemplateParser.prototype.createElement = function (type, child, config) {
	        //针对二维码和条形码均为<barcode>标签，这里做一下处理
	        if (type == 'barcode') {
	            if (this.qrcodeTypeList.indexOf(config['type']) >= 0) {
	                type = 'qrcode';
	            }
	        }
	        // let elementFactory = this.elementProxy[type];
	        var elementFactory = this.elementProxy.get(type); //Reflect.get(this.elementProxy, type);
	        //构造函数
	        var argument_array = [], argument_config = drawElementConfig[type];
	        if (config) {
	            for (var a in argument_config) {
	                var config_val = void 0;
	                var argument_type = argument_config[a]["type"], argument_default = argument_config[a]["default"];
	                switch (argument_type) {
	                    case "number":
	                        config_val = config[a] ? Number(config[a]) : argument_default;
	                        break;
	                    case "boolean":
	                        if (config[a]) {
	                            config_val = config[a] == 'true' ? true : false;
	                        }
	                        else {
	                            config_val = argument_default;
	                        }
	                        break;
	                    default:
	                        config_val = config[a] ? config[a] : argument_default;
	                        break;
	                }
	                argument_array.push(config_val);
	            }
	        }
	        else if (type == 'th') {
	            //针对表头元素，无config，添加默认配置
	            argument_array = [true];
	        }
	        var element = new (elementFactory.bind.apply(elementFactory, [void 0].concat(argument_array)))(); //Reflect.construct(elementFactory, argument_array);
	        //处理非构造参数属性值
	        if (config) {
	            //处理样式属性以外的其他属性
	            for (var key in config) {
	                if (key !== 'style' && !(key in argument_config)) {
	                    element[key] = config[key];
	                    if (key == "_for_") {
	                        child.forEach(function (c) { element.layout(c); });
	                    }
	                }
	            }
	            //处理样式属性
	            if (config.hasOwnProperty("style")) {
	                var styles = config["style"].split(';');
	                for (var _i = 0, styles_1 = styles; _i < styles_1.length; _i++) {
	                    var i = styles_1[_i];
	                    if (i.length > 0) {
	                        var prop_value = i.split(':')[1].trim();
	                        var prop_key = i.split(':')[0].trim();
	                        //处理Boolean类型属性
	                        if (prop_value == "true" || prop_value == "false") {
	                            prop_value = prop_value == "true";
	                        }
	                        //处理number类型属性
	                        if (typeof element[prop_key] == 'number') {
	                            prop_value = prop_value == 'auto' ? prop_value : Number(prop_value);
	                        }
	                        element[prop_key] = prop_value;
	                    }
	                }
	            }
	        }
	        //处理子节点
	        if (typeof child === "string") {
	            if (type === "qrcode") {
	                element.url = child;
	            }
	            else if (type === "text") {
	                element.text = child || "请输入文本内容";
	            }
	            else if (type === "script") {
	                element.text = child;
	            }
	        }
	        else {
	            if (child) {
	                for (var _a = 0, child_1 = child; _a < child_1.length; _a++) {
	                    var j = child_1[_a];
	                    if (type == 'tr') {
	                        element.addCell(j);
	                    }
	                    else if (type == 'table') {
	                        element.addRow(j);
	                    }
	                    else {
	                        element.addChild(j);
	                    }
	                }
	            }
	        }
	        //处理二维码、条形码的渲染问题, 二维码、条形码设置样式后要重新渲染
	        if (type == "barcode" && this.barcodeTypeList.indexOf(config['type']) >= 0) {
	            element["drawObject"]["updateBarcode"]();
	        }
	        else if (type == "qrcode" && this.qrcodeTypeList.indexOf(config['type']) >= 0) {
	            element["drawObject"]["updateQrcode"]();
	        }
	        return element;
	    };
	    TemplateParser.prototype.traverseArray = function (array) {
	        var result = [];
	        for (var m = 0; m < array.length; m++) {
	            var item = array[m];
	            var child = void 0;
	            if (typeof item === 'object') {
	                //object对象, 存在属性节点
	                child = this.traverseObj(item);
	            }
	            result.push(child);
	        }
	        // console.log(result);
	        return result;
	    };
	    TemplateParser.prototype.traverseObj = function (obj) {
	        var result;
	        for (var k in obj) {
	            if (k === 'prop')
	                continue;
	            var item = obj[k];
	            var child = void 0;
	            if (typeof item === 'string' || item == null) {
	                child = item;
	            }
	            else if (item.length > 0) {
	                //array对象, 存在子节点
	                child = this.traverseArray(item);
	            }
	            else if (typeof item === 'object') {
	                //object对象, 存在属性节点
	                child = this.traverseObj(item);
	            }
	            result = this.createElement(k, child, obj.prop);
	        }
	        // console.log(result);
	        return result;
	    };
	    //解析模板方法
	    TemplateParser.prototype.parse = function (input) {
	        if (input !== '') {
	            var jsonObj = void 0;
	            // try{
	            jsonObj = parser.parse(input);
	            return this.traverseObj(jsonObj);
	            // }
	            // catch(error){
	            //     var args = {
	            //         msg: "存在语法错误，请检查",
	            //         type: "error"
	            //     }
	            //     EventManager.broadcast('NEW_TOAST_EVENT',  args);
	            // }
	        }
	    };
	    return TemplateParser;
	}());
	exports.default = TemplateParser;


/***/ }),
/* 94 */
/***/ (function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(process) {(function(e, a) { for(var i in a) e[i] = a[i]; }(exports, /******/ (function(modules) { // webpackBootstrap
	/******/ 	// The module cache
	/******/ 	var installedModules = {};
	
	/******/ 	// The require function
	/******/ 	function __webpack_require__(moduleId) {
	
	/******/ 		// Check if module is in cache
	/******/ 		if(installedModules[moduleId])
	/******/ 			return installedModules[moduleId].exports;
	
	/******/ 		// Create a new module (and put it into the cache)
	/******/ 		var module = installedModules[moduleId] = {
	/******/ 			exports: {},
	/******/ 			id: moduleId,
	/******/ 			loaded: false
	/******/ 		};
	
	/******/ 		// Execute the module function
	/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
	
	/******/ 		// Flag the module as loaded
	/******/ 		module.loaded = true;
	
	/******/ 		// Return the exports of the module
	/******/ 		return module.exports;
	/******/ 	}
	
	
	/******/ 	// expose the modules object (__webpack_modules__)
	/******/ 	__webpack_require__.m = modules;
	
	/******/ 	// expose the module cache
	/******/ 	__webpack_require__.c = installedModules;
	
	/******/ 	// __webpack_public_path__
	/******/ 	__webpack_require__.p = "";
	
	/******/ 	// Load entry module and return exports
	/******/ 	return __webpack_require__(0);
	/******/ })
	/************************************************************************/
	/******/ ([
	/* 0 */
	/***/ (function(module, exports, __webpack_require__) {
	
		/* WEBPACK VAR INJECTION */(function(module) {/* parser generated by jison 0.4.17 */
		/*
		  Returns a Parser object of the following structure:
	
		  Parser: {
		    yy: {}
		  }
	
		  Parser.prototype: {
		    yy: {},
		    trace: function(),
		    symbols_: {associative list: name ==> number},
		    terminals_: {associative list: number ==> name},
		    productions_: [...],
		    performAction: function anonymous(yytext, yyleng, yylineno, yy, yystate, $$, _$),
		    table: [...],
		    defaultActions: {...},
		    parseError: function(str, hash),
		    parse: function(input),
	
		    lexer: {
		        EOF: 1,
		        parseError: function(str, hash),
		        setInput: function(input),
		        input: function(),
		        unput: function(str),
		        more: function(),
		        less: function(n),
		        pastInput: function(),
		        upcomingInput: function(),
		        showPosition: function(),
		        test_match: function(regex_match_array, rule_index),
		        next: function(),
		        lex: function(),
		        begin: function(condition),
		        popState: function(),
		        _currentRules: function(),
		        topState: function(),
		        pushState: function(condition),
	
		        options: {
		            ranges: boolean           (optional: true ==> token location info will include a .range[] member)
		            flex: boolean             (optional: true ==> flex-like lexing behaviour where the rules are tested exhaustively to find the longest match)
		            backtrack_lexer: boolean  (optional: true ==> lexer regexes are tested in order and for each matching regex the action code is invoked; the lexer terminates the scan when a token is returned by the action code)
		        },
	
		        performAction: function(yy, yy_, $avoiding_name_collisions, YY_START),
		        rules: [...],
		        conditions: {associative list: name ==> set},
		    }
		  }
	
	
		  token location info (@$, _$, etc.): {
		    first_line: n,
		    last_line: n,
		    first_column: n,
		    last_column: n,
		    range: [start_number, end_number]       (where the numbers are indexes into the input string, regular zero-based)
		  }
	
	
		  the parseError function receives a 'hash' object with these members for lexer and parser errors: {
		    text:        (matched text)
		    token:       (the produced terminal token, if any)
		    line:        (yylineno)
		  }
		  while parser (grammar) errors will also provide these members, i.e. parser errors deliver a superset of attributes: {
		    loc:         (yylloc)
		    expected:    (string describing the set of expected tokens)
		    recoverable: (boolean: TRUE when the parser has a error recovery rule available for this particular error)
		  }
		*/
		var parser = (function(){
		var o=function(k,v,o,l){for(o=o||{},l=k.length;l--;o[k[l]]=v);return o},$V0=[1,4],$V1=[1,10],$V2=[9,17],$V3=[1,29],$V4=[1,27],$V5=[1,25],$V6=[1,26],$V7=[1,28],$V8=[1,33],$V9=[1,40],$Va=[1,38],$Vb=[6,7,11,12,16,24,25],$Vc=[5,6];
		var parser = {trace: function trace() { },
		yy: {},
		symbols_: {"error":2,"expressions":3,"root":4,"EOF":5,"SPACE":6,"LT":7,"inlabel":8,"GT":9,"elements":10,"ELT":11,"WORD":12,"dom":13,"string":14,"label":15,"SCRIPT":16,"EGT":17,"attributes":18,"attr":19,"attrname":20,"EQ":21,"ATTR":22,"COLON":23,"STRING":24,"CDATA":25,"$accept":0,"$end":1},
		terminals_: {2:"error",5:"EOF",6:"SPACE",7:"LT",9:"GT",11:"ELT",12:"WORD",16:"SCRIPT",17:"EGT",21:"EQ",22:"ATTR",23:"COLON",24:"STRING",25:"CDATA"},
		productions_: [0,[3,2],[3,3],[3,4],[3,3],[3,4],[4,7],[4,6],[4,7],[10,1],[10,2],[10,2],[10,1],[13,1],[13,2],[13,1],[13,2],[15,7],[15,7],[15,6],[15,3],[8,1],[8,2],[8,3],[18,1],[18,2],[18,3],[19,3],[20,1],[20,3],[14,1],[14,2],[14,1],[14,2],[14,2],[14,1],[14,2],[14,2]],
		performAction: function anonymous(yytext, yyleng, yylineno, yy, yystate /* action[1] */, $$ /* vstack */, _$ /* lstack */) {
		/* this == yyval */
	
		var $0 = $$.length - 1;
		switch (yystate) {
		case 1: case 2: case 3:
	
		            console.log("解析成功：");
		            console.log(JSON.stringify($$[$0-1]));
		            return $$[$0-1];
		        
		break;
		case 4: case 5:
	
		            console.log("解析成功：");
		            console.log(JSON.stringify($$[$0-2]));
		            return $$[$0-2];
		        
		break;
		case 6: case 17:
	
		            this.$ = obj($$[$0-3], $$[$0-5]);
		        
		break;
		case 7: case 19:
	
		            this.$ = obj(null, $$[$0-4]);
		        
		break;
		case 8: case 18:
	
		            this.$ = obj(null, $$[$0-5]);
		        
		break;
		case 9:
	
		            this.$ = combine($$[$0])
		        
		break;
		case 10:
	
		            this.$ = combine($$[$0-1])
		        
		break;
		case 11:
	
		            this.$ = combine($$[$0-1], $$[$0]);
		        
		break;
		case 12:
	
		            this.$ = $$[$0]
		        
		break;
		case 13: case 14: case 24: case 28: case 29:
	
		            this.$ = $$[$0];
		        
		break;
		case 15: case 16:
	
		            this.$ = insertScript($$[$0]);
		        
		break;
		case 20:
	
		            this.$ = obj(null, $$[$0-1]);
		        
		break;
		case 21:
	
		            this.$ = parseLabel($$[$0], null);
		        
		break;
		case 22:
	
		            this.$ = parseLabel($$[$0-1], null);
		        
		break;
		case 23:
	
		            this.$ = parseLabel($$[$0-2], $$[$0]);
		        
		break;
		case 25:
	
		            this.$ = $$[$0-1];
		        
		break;
		case 26:
	
		            this.$ = merge($$[$0-2], $$[$0]);
		        
		break;
		case 27:
	
		            this.$ = obj($$[$0].substring(1, $$[$0].length - 1), {key:$$[$0-2].toString()});
		        
		break;
		case 30: case 31: case 32:
	
		            this.$ = $$[$0].toString();
		        
		break;
		case 33:
	
		            this.$ = $$[$0-1].toString();
		        
		break;
		case 34:
	
		            this.$ = $$[$0-1].toString() + $$[$0];
		        
		break;
		case 35:
	
		            this.$ = getCDATA($$[$0]);
		        
		break;
		case 36:
	
		            this.$ = getCDATA($$[$0-1]);
		        
		break;
		case 37:
	
		            this.$ = getCDATA($$[$0-1]) + $$[$0];
		        
		break;
		}
		},
		table: [{3:1,4:2,6:[1,3],7:$V0},{1:[3]},{5:[1,5],6:[1,6]},{4:7,6:[1,8],7:$V0},{8:9,12:$V1},{1:[2,1]},{5:[1,11]},{5:[1,12],6:[1,13]},{4:14,7:$V0},{9:[1,15]},o($V2,[2,21],{6:[1,16]}),{1:[2,4]},{1:[2,2]},{5:[1,17]},{5:[1,18]},{6:[1,21],7:$V3,10:19,11:[1,20],12:$V4,13:22,14:23,15:24,16:$V5,24:$V6,25:$V7},o($V2,[2,22],{18:30,19:31,20:32,12:$V8}),{1:[2,5]},{1:[2,3]},{11:[1,34]},{12:[1,35]},{6:$V9,7:$V3,11:[1,36],12:$V4,14:39,15:37,16:$Va,24:$V6,25:$V7},{6:[1,41],7:$V3,10:42,11:[2,9],12:$V4,13:22,14:23,15:24,16:$V5,24:$V6,25:$V7},{11:[2,12]},o($Vb,[2,13]),o($Vb,[2,15]),{11:[2,30]},{6:[1,43],11:[2,32],12:$V4,14:44,24:$V6,25:$V7},{6:[1,45],11:[2,35],12:$V4,14:46,24:$V6,25:$V7},{8:47,12:$V1},o($V2,[2,23]),o($V2,[2,24],{6:[1,48]}),{21:[1,49]},{21:[2,28],23:[1,50]},{12:[1,51]},{9:[1,52]},{12:[1,53]},o($Vb,[2,14]),o($Vb,[2,16]),{11:[2,31]},{6:$V9,12:$V4,14:39,24:$V6,25:$V7},{6:$V9,7:$V3,11:[2,10],12:$V4,14:39,15:37,16:$Va,24:$V6,25:$V7},{11:[2,11]},{6:$V9,11:[2,33],12:$V4,14:39,24:$V6,25:$V7},{11:[2,34]},{6:$V9,11:[2,36],12:$V4,14:39,24:$V6,25:$V7},{11:[2,37]},{9:[1,54],17:[1,55]},o($V2,[2,25],{19:31,20:32,18:56,12:$V8}),{22:[1,57]},{12:[1,58]},{9:[1,59]},o($Vc,[2,7]),{9:[1,60]},{6:[1,62],7:$V3,10:61,11:[1,63],12:$V4,13:22,14:23,15:24,16:$V5,24:$V6,25:$V7},o($Vb,[2,20]),o($V2,[2,26]),o([6,9,17],[2,27]),{21:[2,29]},o($Vc,[2,6]),o($Vc,[2,8]),{11:[1,64]},{6:$V9,7:$V3,11:[1,65],12:$V4,14:39,15:37,16:$Va,24:$V6,25:$V7},{12:[1,66]},{12:[1,67]},{12:[1,68]},{9:[1,69]},{9:[1,70]},{9:[1,71]},o($Vb,[2,19]),o($Vb,[2,17]),o($Vb,[2,18])],
		defaultActions: {5:[2,1],11:[2,4],12:[2,2],17:[2,5],18:[2,3],23:[2,12],26:[2,30],39:[2,31],42:[2,11],44:[2,34],46:[2,37],58:[2,29]},
		parseError: function parseError(str, hash) {
		    if (hash.recoverable) {
		        this.trace(str);
		    } else {
		        function _parseError (msg, hash) {
		            this.message = msg;
		            this.hash = hash;
		        }
		        _parseError.prototype = Error;
	
		        throw new _parseError(str, hash);
		    }
		},
		parse: function parse(input) {
		    var self = this, stack = [0], tstack = [], vstack = [null], lstack = [], table = this.table, yytext = '', yylineno = 0, yyleng = 0, recovering = 0, TERROR = 2, EOF = 1;
		    var args = lstack.slice.call(arguments, 1);
		    var lexer = Object.create(this.lexer);
		    var sharedState = { yy: {} };
		    for (var k in this.yy) {
		        if (Object.prototype.hasOwnProperty.call(this.yy, k)) {
		            sharedState.yy[k] = this.yy[k];
		        }
		    }
		    lexer.setInput(input, sharedState.yy);
		    sharedState.yy.lexer = lexer;
		    sharedState.yy.parser = this;
		    if (typeof lexer.yylloc == 'undefined') {
		        lexer.yylloc = {};
		    }
		    var yyloc = lexer.yylloc;
		    lstack.push(yyloc);
		    var ranges = lexer.options && lexer.options.ranges;
		    if (typeof sharedState.yy.parseError === 'function') {
		        this.parseError = sharedState.yy.parseError;
		    } else {
		        this.parseError = Object.getPrototypeOf(this).parseError;
		    }
		    function popStack(n) {
		        stack.length = stack.length - 2 * n;
		        vstack.length = vstack.length - n;
		        lstack.length = lstack.length - n;
		    }
		    _token_stack:
		        var lex = function () {
		            var token;
		            token = lexer.lex() || EOF;
		            if (typeof token !== 'number') {
		                token = self.symbols_[token] || token;
		            }
		            return token;
		        };
		    var symbol, preErrorSymbol, state, action, a, r, yyval = {}, p, len, newState, expected;
		    while (true) {
		        state = stack[stack.length - 1];
		        if (this.defaultActions[state]) {
		            action = this.defaultActions[state];
		        } else {
		            if (symbol === null || typeof symbol == 'undefined') {
		                symbol = lex();
		            }
		            action = table[state] && table[state][symbol];
		        }
		                    if (typeof action === 'undefined' || !action.length || !action[0]) {
		                var errStr = '';
		                expected = [];
		                for (p in table[state]) {
		                    if (this.terminals_[p] && p > TERROR) {
		                        expected.push('\'' + this.terminals_[p] + '\'');
		                    }
		                }
		                if (lexer.showPosition) {
		                    errStr = 'Parse error on line ' + (yylineno + 1) + ':\n' + lexer.showPosition() + '\nExpecting ' + expected.join(', ') + ', got \'' + (this.terminals_[symbol] || symbol) + '\'';
		                } else {
		                    errStr = 'Parse error on line ' + (yylineno + 1) + ': Unexpected ' + (symbol == EOF ? 'end of input' : '\'' + (this.terminals_[symbol] || symbol) + '\'');
		                }
		                this.parseError(errStr, {
		                    text: lexer.match,
		                    token: this.terminals_[symbol] || symbol,
		                    line: lexer.yylineno,
		                    loc: yyloc,
		                    expected: expected
		                });
		            }
		        if (action[0] instanceof Array && action.length > 1) {
		            throw new Error('Parse Error: multiple actions possible at state: ' + state + ', token: ' + symbol);
		        }
		        switch (action[0]) {
		        case 1:
		            stack.push(symbol);
		            vstack.push(lexer.yytext);
		            lstack.push(lexer.yylloc);
		            stack.push(action[1]);
		            symbol = null;
		            if (!preErrorSymbol) {
		                yyleng = lexer.yyleng;
		                yytext = lexer.yytext;
		                yylineno = lexer.yylineno;
		                yyloc = lexer.yylloc;
		                if (recovering > 0) {
		                    recovering--;
		                }
		            } else {
		                symbol = preErrorSymbol;
		                preErrorSymbol = null;
		            }
		            break;
		        case 2:
		            len = this.productions_[action[1]][1];
		            yyval.$ = vstack[vstack.length - len];
		            yyval._$ = {
		                first_line: lstack[lstack.length - (len || 1)].first_line,
		                last_line: lstack[lstack.length - 1].last_line,
		                first_column: lstack[lstack.length - (len || 1)].first_column,
		                last_column: lstack[lstack.length - 1].last_column
		            };
		            if (ranges) {
		                yyval._$.range = [
		                    lstack[lstack.length - (len || 1)].range[0],
		                    lstack[lstack.length - 1].range[1]
		                ];
		            }
		            r = this.performAction.apply(yyval, [
		                yytext,
		                yyleng,
		                yylineno,
		                sharedState.yy,
		                action[1],
		                vstack,
		                lstack
		            ].concat(args));
		            if (typeof r !== 'undefined') {
		                return r;
		            }
		            if (len) {
		                stack = stack.slice(0, -1 * len * 2);
		                vstack = vstack.slice(0, -1 * len);
		                lstack = lstack.slice(0, -1 * len);
		            }
		            stack.push(this.productions_[action[1]][0]);
		            vstack.push(yyval.$);
		            lstack.push(yyval._$);
		            newState = table[stack[stack.length - 2]][stack[stack.length - 1]];
		            stack.push(newState);
		            break;
		        case 3:
		            return true;
		        }
		    }
		    return true;
		}};
	
		    function obj(value, attr) {
		        var obj = {},
		            key = attr["key"];
	
		        obj[key] = value;
		        if(attr){
		            obj.prop = attr["attr"];
		        }
		        return obj;
		    }
	
		    function combine(arr1, arr2) {
		        var result = [];
	
		        result.push(arr1);
		        if(arr2){
		            if(arr2.length > 0){
		                for(var i = 0; i < arr2.length; i++){
		                    result.push(arr2[i]);               
		                } 
		            } else {
		                result.push(arr2);               
		            }
		        }
	
		        return result;
		    }
	
		    function merge(o1, o2) {
		        var obj = {};
	
		        for(var k in o1) {
		        obj[k] = o1[k];
		        }
		        for(var v in o2) {
		        obj[v] = o2[v];
		        }
	
		        return obj;
		    }
	
		    function parseLabel(key, attr){
		        return {
		            key: key,
		            attr: attr
		        }
		    }
	
		    function getKey(){
	
		    }
	
		    function addAttr(attr){
		        var result = {};
	
		        for(var k in attr){
		            result[k] = attr[k];
		        }
		        return result;
		    }
	
		    function insertScript(str){
		        var obj = {};
	
		        obj['script'] = str;
		        return obj;
		    }
	
		    function getCDATA(str){
		        var slice_start = 8,
		            slice_end = str.indexOf("]]>"),
		            key = "";
		        
		        key = str.substring(slice_start + 1, slice_end);
		        return key;
		    }
		/* generated by jison-lex 0.3.4 */
		var lexer = (function(){
		var lexer = ({
	
		EOF:1,
	
		parseError:function parseError(str, hash) {
		        if (this.yy.parser) {
		            this.yy.parser.parseError(str, hash);
		        } else {
		            throw new Error(str);
		        }
		    },
	
		// resets the lexer, sets new input
		setInput:function (input, yy) {
		        this.yy = yy || this.yy || {};
		        this._input = input;
		        this._more = this._backtrack = this.done = false;
		        this.yylineno = this.yyleng = 0;
		        this.yytext = this.matched = this.match = '';
		        this.conditionStack = ['INITIAL'];
		        this.yylloc = {
		            first_line: 1,
		            first_column: 0,
		            last_line: 1,
		            last_column: 0
		        };
		        if (this.options.ranges) {
		            this.yylloc.range = [0,0];
		        }
		        this.offset = 0;
		        return this;
		    },
	
		// consumes and returns one char from the input
		input:function () {
		        var ch = this._input[0];
		        this.yytext += ch;
		        this.yyleng++;
		        this.offset++;
		        this.match += ch;
		        this.matched += ch;
		        var lines = ch.match(/(?:\r\n?|\n).*/g);
		        if (lines) {
		            this.yylineno++;
		            this.yylloc.last_line++;
		        } else {
		            this.yylloc.last_column++;
		        }
		        if (this.options.ranges) {
		            this.yylloc.range[1]++;
		        }
	
		        this._input = this._input.slice(1);
		        return ch;
		    },
	
		// unshifts one char (or a string) into the input
		unput:function (ch) {
		        var len = ch.length;
		        var lines = ch.split(/(?:\r\n?|\n)/g);
	
		        this._input = ch + this._input;
		        this.yytext = this.yytext.substr(0, this.yytext.length - len);
		        //this.yyleng -= len;
		        this.offset -= len;
		        var oldLines = this.match.split(/(?:\r\n?|\n)/g);
		        this.match = this.match.substr(0, this.match.length - 1);
		        this.matched = this.matched.substr(0, this.matched.length - 1);
	
		        if (lines.length - 1) {
		            this.yylineno -= lines.length - 1;
		        }
		        var r = this.yylloc.range;
	
		        this.yylloc = {
		            first_line: this.yylloc.first_line,
		            last_line: this.yylineno + 1,
		            first_column: this.yylloc.first_column,
		            last_column: lines ?
		                (lines.length === oldLines.length ? this.yylloc.first_column : 0)
		                 + oldLines[oldLines.length - lines.length].length - lines[0].length :
		              this.yylloc.first_column - len
		        };
	
		        if (this.options.ranges) {
		            this.yylloc.range = [r[0], r[0] + this.yyleng - len];
		        }
		        this.yyleng = this.yytext.length;
		        return this;
		    },
	
		// When called from action, caches matched text and appends it on next action
		more:function () {
		        this._more = true;
		        return this;
		    },
	
		// When called from action, signals the lexer that this rule fails to match the input, so the next matching rule (regex) should be tested instead.
		reject:function () {
		        if (this.options.backtrack_lexer) {
		            this._backtrack = true;
		        } else {
		            return this.parseError('Lexical error on line ' + (this.yylineno + 1) + '. You can only invoke reject() in the lexer when the lexer is of the backtracking persuasion (options.backtrack_lexer = true).\n' + this.showPosition(), {
		                text: "",
		                token: null,
		                line: this.yylineno
		            });
	
		        }
		        return this;
		    },
	
		// retain first n characters of the match
		less:function (n) {
		        this.unput(this.match.slice(n));
		    },
	
		// displays already matched input, i.e. for error messages
		pastInput:function () {
		        var past = this.matched.substr(0, this.matched.length - this.match.length);
		        return (past.length > 20 ? '...':'') + past.substr(-20).replace(/\n/g, "");
		    },
	
		// displays upcoming input, i.e. for error messages
		upcomingInput:function () {
		        var next = this.match;
		        if (next.length < 20) {
		            next += this._input.substr(0, 20-next.length);
		        }
		        return (next.substr(0,20) + (next.length > 20 ? '...' : '')).replace(/\n/g, "");
		    },
	
		// displays the character position where the lexing error occurred, i.e. for error messages
		showPosition:function () {
		        var pre = this.pastInput();
		        var c = new Array(pre.length + 1).join("-");
		        return pre + this.upcomingInput() + "\n" + c + "^";
		    },
	
		// test the lexed token: return FALSE when not a match, otherwise return token
		test_match:function (match, indexed_rule) {
		        var token,
		            lines,
		            backup;
	
		        if (this.options.backtrack_lexer) {
		            // save context
		            backup = {
		                yylineno: this.yylineno,
		                yylloc: {
		                    first_line: this.yylloc.first_line,
		                    last_line: this.last_line,
		                    first_column: this.yylloc.first_column,
		                    last_column: this.yylloc.last_column
		                },
		                yytext: this.yytext,
		                match: this.match,
		                matches: this.matches,
		                matched: this.matched,
		                yyleng: this.yyleng,
		                offset: this.offset,
		                _more: this._more,
		                _input: this._input,
		                yy: this.yy,
		                conditionStack: this.conditionStack.slice(0),
		                done: this.done
		            };
		            if (this.options.ranges) {
		                backup.yylloc.range = this.yylloc.range.slice(0);
		            }
		        }
	
		        lines = match[0].match(/(?:\r\n?|\n).*/g);
		        if (lines) {
		            this.yylineno += lines.length;
		        }
		        this.yylloc = {
		            first_line: this.yylloc.last_line,
		            last_line: this.yylineno + 1,
		            first_column: this.yylloc.last_column,
		            last_column: lines ?
		                         lines[lines.length - 1].length - lines[lines.length - 1].match(/\r?\n?/)[0].length :
		                         this.yylloc.last_column + match[0].length
		        };
		        this.yytext += match[0];
		        this.match += match[0];
		        this.matches = match;
		        this.yyleng = this.yytext.length;
		        if (this.options.ranges) {
		            this.yylloc.range = [this.offset, this.offset += this.yyleng];
		        }
		        this._more = false;
		        this._backtrack = false;
		        this._input = this._input.slice(match[0].length);
		        this.matched += match[0];
		        token = this.performAction.call(this, this.yy, this, indexed_rule, this.conditionStack[this.conditionStack.length - 1]);
		        if (this.done && this._input) {
		            this.done = false;
		        }
		        if (token) {
		            return token;
		        } else if (this._backtrack) {
		            // recover context
		            for (var k in backup) {
		                this[k] = backup[k];
		            }
		            return false; // rule action called reject() implying the next rule should be tested instead.
		        }
		        return false;
		    },
	
		// return next match in input
		next:function () {
		        if (this.done) {
		            return this.EOF;
		        }
		        if (!this._input) {
		            this.done = true;
		        }
	
		        var token,
		            match,
		            tempMatch,
		            index;
		        if (!this._more) {
		            this.yytext = '';
		            this.match = '';
		        }
		        var rules = this._currentRules();
		        for (var i = 0; i < rules.length; i++) {
		            tempMatch = this._input.match(this.rules[rules[i]]);
		            if (tempMatch && (!match || tempMatch[0].length > match[0].length)) {
		                match = tempMatch;
		                index = i;
		                if (this.options.backtrack_lexer) {
		                    token = this.test_match(tempMatch, rules[i]);
		                    if (token !== false) {
		                        return token;
		                    } else if (this._backtrack) {
		                        match = false;
		                        continue; // rule action called reject() implying a rule MISmatch.
		                    } else {
		                        // else: this is a lexer rule which consumes input without producing a token (e.g. whitespace)
		                        return false;
		                    }
		                } else if (!this.options.flex) {
		                    break;
		                }
		            }
		        }
		        if (match) {
		            token = this.test_match(match, rules[index]);
		            if (token !== false) {
		                return token;
		            }
		            // else: this is a lexer rule which consumes input without producing a token (e.g. whitespace)
		            return false;
		        }
		        if (this._input === "") {
		            return this.EOF;
		        } else {
		            return this.parseError('Lexical error on line ' + (this.yylineno + 1) + '. Unrecognized text.\n' + this.showPosition(), {
		                text: "",
		                token: null,
		                line: this.yylineno
		            });
		        }
		    },
	
		// return next match that has a token
		lex:function lex() {
		        var r = this.next();
		        if (r) {
		            return r;
		        } else {
		            return this.lex();
		        }
		    },
	
		// activates a new lexer condition state (pushes the new lexer condition state onto the condition stack)
		begin:function begin(condition) {
		        this.conditionStack.push(condition);
		    },
	
		// pop the previously active lexer condition state off the condition stack
		popState:function popState() {
		        var n = this.conditionStack.length - 1;
		        if (n > 0) {
		            return this.conditionStack.pop();
		        } else {
		            return this.conditionStack[0];
		        }
		    },
	
		// produce the lexer rule set which is active for the currently active lexer condition state
		_currentRules:function _currentRules() {
		        if (this.conditionStack.length && this.conditionStack[this.conditionStack.length - 1]) {
		            return this.conditions[this.conditionStack[this.conditionStack.length - 1]].rules;
		        } else {
		            return this.conditions["INITIAL"].rules;
		        }
		    },
	
		// return the currently active lexer condition state; when an index argument is provided it produces the N-th previous condition state, if available
		topState:function topState(n) {
		        n = this.conditionStack.length - 1 - Math.abs(n || 0);
		        if (n >= 0) {
		            return this.conditionStack[n];
		        } else {
		            return "INITIAL";
		        }
		    },
	
		// alias for begin(condition)
		pushState:function pushState(condition) {
		        this.begin(condition);
		    },
	
		// return the number of states currently on the stack
		stateStackSize:function stateStackSize() {
		        return this.conditionStack.length;
		    },
		options: {},
		performAction: function anonymous(yy,yy_,$avoiding_name_collisions,YY_START) {
		var YYSTATE=YY_START;
		switch($avoiding_name_collisions) {
		case 0:/* skip comment */
		break;
		case 1:/* skip comment */
		break;
		case 2:return "SPACE"
		break;
		case 3:return "CDATA"
		break;
		case 4:return "SCRIPT"
		break;
		case 5:return "ELT" 
		break;
		case 6:return "EGT" 
		break;
		case 7:return "LT"
		break;
		case 8:return "GT"
		break;
		case 9:return "EQ"
		break;
		case 10:return "COLON"
		break;
		case 11:return 22
		break;
		case 12:return 12
		break;
		case 13:return 24
		break;
		case 14:return 5
		break;
		}
		},
		rules: [/^(?:<\?(([\s]*)(?!\?>).)*[\s]*\?>)/,/^(?:<!--(([\s]*)(?!-->).)*[\s]*-->)/,/^(?:[\r\n\s]+)/,/^(?:<!\[CDATA\[(([\s]*)(?!\]\]>).)*[\s]*\]\]>)/,/^(?:<%(([\s]*)(?!%>).)*[\s]*%>)/,/^(?:<\/)/,/^(?:\/>)/,/^(?:<)/,/^(?:>)/,/^(?:=)/,/^(?::)/,/^(?:"(\\["]|[^"])*")/,/^(?:([^<%>\s=\/:])+)/,/^(?:([^\r\n])+)/,/^(?:$)/],
		conditions: {"INITIAL":{"rules":[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14],"inclusive":true}}
		});
		return lexer;
		})();
		parser.lexer = lexer;
		function Parser () {
		  this.yy = {};
		}
		Parser.prototype = parser;parser.Parser = Parser;
		return new Parser;
		})();
	
	
		if (true) {
		exports.parser = parser;
		exports.Parser = parser.Parser;
		exports.parse = function () { return parser.parse.apply(parser, arguments); };
		}
		/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(1)(module)))
	
	/***/ }),
	/* 1 */
	/***/ (function(module, exports) {
	
		module.exports = function(module) {
			if(!module.webpackPolyfill) {
				module.deprecate = function() {};
				module.paths = [];
				// module.parent = undefined by default
				module.children = [];
				module.webpackPolyfill = 1;
			}
			return module;
		}
	
	
	/***/ }),
	/* 2 */
	/***/ (function(module, exports) {
	
		
	
	/***/ }),
	/* 3 */
	/***/ (function(module, exports) {
	
		// Copyright Joyent, Inc. and other Node contributors.
		//
		// Permission is hereby granted, free of charge, to any person obtaining a
		// copy of this software and associated documentation files (the
		// "Software"), to deal in the Software without restriction, including
		// without limitation the rights to use, copy, modify, merge, publish,
		// distribute, sublicense, and/or sell copies of the Software, and to permit
		// persons to whom the Software is furnished to do so, subject to the
		// following conditions:
		//
		// The above copyright notice and this permission notice shall be included
		// in all copies or substantial portions of the Software.
		//
		// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
		// OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
		// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
		// NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
		// DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
		// OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
		// USE OR OTHER DEALINGS IN THE SOFTWARE.
	
		// resolves . and .. elements in a path array with directory names there
		// must be no slashes, empty elements, or device names (c:\) in the array
		// (so also no leading and trailing slashes - it does not distinguish
		// relative and absolute paths)
		function normalizeArray(parts, allowAboveRoot) {
		  // if the path tries to go above the root, `up` ends up > 0
		  var up = 0;
		  for (var i = parts.length - 1; i >= 0; i--) {
		    var last = parts[i];
		    if (last === '.') {
		      parts.splice(i, 1);
		    } else if (last === '..') {
		      parts.splice(i, 1);
		      up++;
		    } else if (up) {
		      parts.splice(i, 1);
		      up--;
		    }
		  }
	
		  // if the path is allowed to go above the root, restore leading ..s
		  if (allowAboveRoot) {
		    for (; up--; up) {
		      parts.unshift('..');
		    }
		  }
	
		  return parts;
		}
	
		// Split a filename into [root, dir, basename, ext], unix version
		// 'root' is just a slash, or nothing.
		var splitPathRe =
		    /^(\/?|)([\s\S]*?)((?:\.{1,2}|[^\/]+?|)(\.[^.\/]*|))(?:[\/]*)$/;
		var splitPath = function(filename) {
		  return splitPathRe.exec(filename).slice(1);
		};
	
		// path.resolve([from ...], to)
		// posix version
		exports.resolve = function() {
		  var resolvedPath = '',
		      resolvedAbsolute = false;
	
		  for (var i = arguments.length - 1; i >= -1 && !resolvedAbsolute; i--) {
		    var path = (i >= 0) ? arguments[i] : process.cwd();
	
		    // Skip empty and invalid entries
		    if (typeof path !== 'string') {
		      throw new TypeError('Arguments to path.resolve must be strings');
		    } else if (!path) {
		      continue;
		    }
	
		    resolvedPath = path + '/' + resolvedPath;
		    resolvedAbsolute = path.charAt(0) === '/';
		  }
	
		  // At this point the path should be resolved to a full absolute path, but
		  // handle relative paths to be safe (might happen when process.cwd() fails)
	
		  // Normalize the path
		  resolvedPath = normalizeArray(filter(resolvedPath.split('/'), function(p) {
		    return !!p;
		  }), !resolvedAbsolute).join('/');
	
		  return ((resolvedAbsolute ? '/' : '') + resolvedPath) || '.';
		};
	
		// path.normalize(path)
		// posix version
		exports.normalize = function(path) {
		  var isAbsolute = exports.isAbsolute(path),
		      trailingSlash = substr(path, -1) === '/';
	
		  // Normalize the path
		  path = normalizeArray(filter(path.split('/'), function(p) {
		    return !!p;
		  }), !isAbsolute).join('/');
	
		  if (!path && !isAbsolute) {
		    path = '.';
		  }
		  if (path && trailingSlash) {
		    path += '/';
		  }
	
		  return (isAbsolute ? '/' : '') + path;
		};
	
		// posix version
		exports.isAbsolute = function(path) {
		  return path.charAt(0) === '/';
		};
	
		// posix version
		exports.join = function() {
		  var paths = Array.prototype.slice.call(arguments, 0);
		  return exports.normalize(filter(paths, function(p, index) {
		    if (typeof p !== 'string') {
		      throw new TypeError('Arguments to path.join must be strings');
		    }
		    return p;
		  }).join('/'));
		};
	
	
		// path.relative(from, to)
		// posix version
		exports.relative = function(from, to) {
		  from = exports.resolve(from).substr(1);
		  to = exports.resolve(to).substr(1);
	
		  function trim(arr) {
		    var start = 0;
		    for (; start < arr.length; start++) {
		      if (arr[start] !== '') break;
		    }
	
		    var end = arr.length - 1;
		    for (; end >= 0; end--) {
		      if (arr[end] !== '') break;
		    }
	
		    if (start > end) return [];
		    return arr.slice(start, end - start + 1);
		  }
	
		  var fromParts = trim(from.split('/'));
		  var toParts = trim(to.split('/'));
	
		  var length = Math.min(fromParts.length, toParts.length);
		  var samePartsLength = length;
		  for (var i = 0; i < length; i++) {
		    if (fromParts[i] !== toParts[i]) {
		      samePartsLength = i;
		      break;
		    }
		  }
	
		  var outputParts = [];
		  for (var i = samePartsLength; i < fromParts.length; i++) {
		    outputParts.push('..');
		  }
	
		  outputParts = outputParts.concat(toParts.slice(samePartsLength));
	
		  return outputParts.join('/');
		};
	
		exports.sep = '/';
		exports.delimiter = ':';
	
		exports.dirname = function(path) {
		  var result = splitPath(path),
		      root = result[0],
		      dir = result[1];
	
		  if (!root && !dir) {
		    // No dirname whatsoever
		    return '.';
		  }
	
		  if (dir) {
		    // It has a dirname, strip trailing slash
		    dir = dir.substr(0, dir.length - 1);
		  }
	
		  return root + dir;
		};
	
	
		exports.basename = function(path, ext) {
		  var f = splitPath(path)[2];
		  // TODO: make this comparison case-insensitive on windows?
		  if (ext && f.substr(-1 * ext.length) === ext) {
		    f = f.substr(0, f.length - ext.length);
		  }
		  return f;
		};
	
	
		exports.extname = function(path) {
		  return splitPath(path)[3];
		};
	
		function filter (xs, f) {
		    if (xs.filter) return xs.filter(f);
		    var res = [];
		    for (var i = 0; i < xs.length; i++) {
		        if (f(xs[i], i, xs)) res.push(xs[i]);
		    }
		    return res;
		}
	
		// String.prototype.substr - negative index don't work in IE8
		var substr = 'ab'.substr(-1) === 'b'
		    ? function (str, start, len) { return str.substr(start, len) }
		    : function (str, start, len) {
		        if (start < 0) start = str.length + start;
		        return str.substr(start, len);
		    }
		;
	
	
	/***/ })
	/******/ ])));
	/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(95)))

/***/ }),
/* 95 */
/***/ (function(module, exports) {

	// shim for using process in browser
	var process = module.exports = {};
	
	// cached from whatever global is present so that test runners that stub it
	// don't break things.  But we need to wrap it in a try catch in case it is
	// wrapped in strict mode code which doesn't define any globals.  It's inside a
	// function because try/catches deoptimize in certain engines.
	
	var cachedSetTimeout;
	var cachedClearTimeout;
	
	function defaultSetTimout() {
	    throw new Error('setTimeout has not been defined');
	}
	function defaultClearTimeout () {
	    throw new Error('clearTimeout has not been defined');
	}
	(function () {
	    try {
	        if (typeof setTimeout === 'function') {
	            cachedSetTimeout = setTimeout;
	        } else {
	            cachedSetTimeout = defaultSetTimout;
	        }
	    } catch (e) {
	        cachedSetTimeout = defaultSetTimout;
	    }
	    try {
	        if (typeof clearTimeout === 'function') {
	            cachedClearTimeout = clearTimeout;
	        } else {
	            cachedClearTimeout = defaultClearTimeout;
	        }
	    } catch (e) {
	        cachedClearTimeout = defaultClearTimeout;
	    }
	} ())
	function runTimeout(fun) {
	    if (cachedSetTimeout === setTimeout) {
	        //normal enviroments in sane situations
	        return setTimeout(fun, 0);
	    }
	    // if setTimeout wasn't available but was latter defined
	    if ((cachedSetTimeout === defaultSetTimout || !cachedSetTimeout) && setTimeout) {
	        cachedSetTimeout = setTimeout;
	        return setTimeout(fun, 0);
	    }
	    try {
	        // when when somebody has screwed with setTimeout but no I.E. maddness
	        return cachedSetTimeout(fun, 0);
	    } catch(e){
	        try {
	            // When we are in I.E. but the script has been evaled so I.E. doesn't trust the global object when called normally
	            return cachedSetTimeout.call(null, fun, 0);
	        } catch(e){
	            // same as above but when it's a version of I.E. that must have the global object for 'this', hopfully our context correct otherwise it will throw a global error
	            return cachedSetTimeout.call(this, fun, 0);
	        }
	    }
	
	
	}
	function runClearTimeout(marker) {
	    if (cachedClearTimeout === clearTimeout) {
	        //normal enviroments in sane situations
	        return clearTimeout(marker);
	    }
	    // if clearTimeout wasn't available but was latter defined
	    if ((cachedClearTimeout === defaultClearTimeout || !cachedClearTimeout) && clearTimeout) {
	        cachedClearTimeout = clearTimeout;
	        return clearTimeout(marker);
	    }
	    try {
	        // when when somebody has screwed with setTimeout but no I.E. maddness
	        return cachedClearTimeout(marker);
	    } catch (e){
	        try {
	            // When we are in I.E. but the script has been evaled so I.E. doesn't  trust the global object when called normally
	            return cachedClearTimeout.call(null, marker);
	        } catch (e){
	            // same as above but when it's a version of I.E. that must have the global object for 'this', hopfully our context correct otherwise it will throw a global error.
	            // Some versions of I.E. have different rules for clearTimeout vs setTimeout
	            return cachedClearTimeout.call(this, marker);
	        }
	    }
	
	
	
	}
	var queue = [];
	var draining = false;
	var currentQueue;
	var queueIndex = -1;
	
	function cleanUpNextTick() {
	    if (!draining || !currentQueue) {
	        return;
	    }
	    draining = false;
	    if (currentQueue.length) {
	        queue = currentQueue.concat(queue);
	    } else {
	        queueIndex = -1;
	    }
	    if (queue.length) {
	        drainQueue();
	    }
	}
	
	function drainQueue() {
	    if (draining) {
	        return;
	    }
	    var timeout = runTimeout(cleanUpNextTick);
	    draining = true;
	
	    var len = queue.length;
	    while(len) {
	        currentQueue = queue;
	        queue = [];
	        while (++queueIndex < len) {
	            if (currentQueue) {
	                currentQueue[queueIndex].run();
	            }
	        }
	        queueIndex = -1;
	        len = queue.length;
	    }
	    currentQueue = null;
	    draining = false;
	    runClearTimeout(timeout);
	}
	
	process.nextTick = function (fun) {
	    var args = new Array(arguments.length - 1);
	    if (arguments.length > 1) {
	        for (var i = 1; i < arguments.length; i++) {
	            args[i - 1] = arguments[i];
	        }
	    }
	    queue.push(new Item(fun, args));
	    if (queue.length === 1 && !draining) {
	        runTimeout(drainQueue);
	    }
	};
	
	// v8 likes predictible objects
	function Item(fun, array) {
	    this.fun = fun;
	    this.array = array;
	}
	Item.prototype.run = function () {
	    this.fun.apply(null, this.array);
	};
	process.title = 'browser';
	process.browser = true;
	process.env = {};
	process.argv = [];
	process.version = ''; // empty string to avoid regexp issues
	process.versions = {};
	
	function noop() {}
	
	process.on = noop;
	process.addListener = noop;
	process.once = noop;
	process.off = noop;
	process.removeListener = noop;
	process.removeAllListeners = noop;
	process.emit = noop;
	process.prependListener = noop;
	process.prependOnceListener = noop;
	
	process.listeners = function (name) { return [] }
	
	process.binding = function (name) {
	    throw new Error('process.binding is not supported');
	};
	
	process.cwd = function () { return '/' };
	process.chdir = function (dir) {
	    throw new Error('process.chdir is not supported');
	};
	process.umask = function() { return 0; };


/***/ }),
/* 96 */
/***/ (function(module, exports) {

	module.exports = {"page":{"width":{"type":"number","default":null},"height":{"type":"number","default":null}},"layout":{"left":{"type":"number","default":null},"top":{"type":"number","default":null},"width":{"type":"number","default":null},"height":{"type":"number","default":null}},"header":{"width":{"type":"number","default":null},"height":{"type":"number","default":null}},"footer":{"width":{"type":"number","default":null},"height":{"type":"number","default":null}},"text":{"orientation":{"type":"string","default":"horizontal"},"width":{"type":"number","default":null},"height":{"type":"number","default":null}},"line":{"orientation":{"type":"string","default":"horizontal"}},"rect":{"left":{"type":"number","default":10},"top":{"type":"number","default":10},"width":{"type":"number","default":26},"height":{"type":"number","default":26}},"image":{"src":{"type":"string","default":null},"left":{"type":"number","default":10},"top":{"type":"number","default":10},"width":{"type":"number","default":null},"height":{"type":"number","default":null},"page":{"type":"PageDrawElement","default":null}},"qrcode":{"url":{"type":"string","default":null},"left":{"type":"number","default":10},"top":{"type":"number","default":10},"width":{"type":"number","default":30},"height":{"type":"number","default":30},"page":{"type":"PageDrawElement","default":null}},"barcode":{"url":{"type":"string","default":null},"left":{"type":"number","default":10},"top":{"type":"number","default":10},"width":{"type":"number","default":40},"height":{"type":"number","default":24},"page":{"type":"PageDrawElement","default":null}},"table":{"width":{"type":"number","default":null},"col":{"type":"number","default":0},"row":{"type":"number","default":0}},"td":{"isThead":{"type":"boolean","default":false}},"th":{"isThead":{"type":"boolean","default":true}},"tr":{},"script":{"text":{"type":"string","default":"<% %>"}},"pageIndex":{"width":{"type":"number","default":null},"height":{"type":"number","default":null}}}

/***/ }),
/* 97 */
/***/ (function(module, exports, __webpack_require__) {

	var map = {
		"./BarcodeDrawElement": 38,
		"./BarcodeDrawElement.ts": 38,
		"./ComponentDrawElement": 83,
		"./ComponentDrawElement.ts": 83,
		"./FooterDrawElement": 85,
		"./FooterDrawElement.ts": 85,
		"./HeaderDrawElement": 84,
		"./HeaderDrawElement.ts": 84,
		"./ImageDrawElement": 37,
		"./ImageDrawElement.ts": 37,
		"./Index": 26,
		"./Index.ts": 26,
		"./LayoutDrawElement": 27,
		"./LayoutDrawElement.ts": 27,
		"./LineDrawElement": 36,
		"./LineDrawElement.ts": 36,
		"./PageDrawElement": 34,
		"./PageDrawElement.ts": 34,
		"./PageIndexDrawElement": 86,
		"./PageIndexDrawElement.ts": 86,
		"./QrcodeDrawElement": 81,
		"./QrcodeDrawElement.ts": 81,
		"./RectDrawElement": 35,
		"./RectDrawElement.ts": 35,
		"./ScriptDrawElement": 82,
		"./ScriptDrawElement.ts": 82,
		"./TableCellDrawElement": 32,
		"./TableCellDrawElement.ts": 32,
		"./TableDrawElement": 29,
		"./TableDrawElement.ts": 29,
		"./TableRowDrawElement": 31,
		"./TableRowDrawElement.ts": 31,
		"./TextDrawElement": 33,
		"./TextDrawElement.ts": 33
	};
	function webpackContext(req) {
		return __webpack_require__(webpackContextResolve(req));
	};
	function webpackContextResolve(req) {
		return map[req] || (function() { throw new Error("Cannot find module '" + req + "'.") }());
	};
	webpackContext.keys = function webpackContextKeys() {
		return Object.keys(map);
	};
	webpackContext.resolve = webpackContextResolve;
	module.exports = webpackContext;
	webpackContext.id = 97;


/***/ }),
/* 98 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	var TOPClient_1 = __webpack_require__(99);
	var HTTPClient_1 = __webpack_require__(136);
	//服务接口的相关配置
	var _ServiceConfig = null;
	var _TopConfig = null;
	function init(config) {
	    _ServiceConfig = config.serviceConfig;
	    _TopConfig = config.topConfig ? config.topConfig : null;
	}
	exports.init = init;
	function get(serviceId, data) {
	    return ajaxFactory(serviceId, "GET", false, data);
	}
	exports.get = get;
	function post(serviceId, data) {
	    return ajaxFactory(serviceId, "POST", false, data);
	}
	exports.post = post;
	function upload(serviceId, data) {
	    return ajaxFactory(serviceId, "POST", true, data);
	}
	exports.upload = upload;
	var ajaxFactory = function (serviceId, ajaxType, isUpload, data) {
	    var requestInfo = getRequestInfo(serviceId);
	    var client;
	    var linkType = requestInfo["linkType"];
	    var url = requestInfo["url"];
	    switch (linkType) {
	        case 'TOP':
	            if (_TopConfig) {
	                client = new TOPClient_1.default(data, _TopConfig);
	            }
	            else {
	                throw new Error("缺少TOP接口调用的相关配置信息，请调用init()方法传入");
	            }
	            break;
	        case 'HTTP':
	            client = new HTTPClient_1.default(data);
	            break;
	        default:
	            client = new HTTPClient_1.default(data);
	            break;
	    }
	    return client.connect(url, ajaxType, isUpload, data);
	};
	//根据服务接口id获取接口Top或Http接口信息
	var getRequestInfo = function (id) {
	    var url;
	    var linkType;
	    if (_ServiceConfig.hasOwnProperty("request")) {
	        _ServiceConfig["request"].forEach(function (item) {
	            var path = item.path || "";
	            var apis = item.api || null;
	            if (apis) {
	                apis.forEach(function (api) {
	                    if (api.hasOwnProperty("id")) {
	                        if (api.id == id) {
	                            if (api.hasOwnProperty("top")) {
	                                url = api.top;
	                                linkType = "TOP";
	                            }
	                            else if (api.hasOwnProperty("http")) {
	                                url = path + api.http;
	                                linkType = "HTTP";
	                            }
	                        }
	                    }
	                });
	                if (!url) {
	                    throw new Error("id:" + id + "未找到相关接口配置，请检查！");
	                }
	            }
	            else {
	                throw new Error("没有输入服务接口相关配置，请调用init()方法传入配置后重试！");
	            }
	        });
	    }
	    else {
	        throw new Error("没有输入服务接口相关配置，请调用init()方法传入配置后重试！");
	    }
	    return {
	        url: url,
	        linkType: linkType
	    };
	};


/***/ }),
/* 99 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var ClientFactory_1 = __webpack_require__(100);
	var CryptoJS = __webpack_require__(101);
	var topRestConfig = __webpack_require__(135); //元素构造函数的配置信息
	var TOPClient = (function (_super) {
	    __extends(TOPClient, _super);
	    function TOPClient(cumsData, topConfig) {
	        var _this = _super.call(this, cumsData) || this;
	        _this.app_key = "cneditor"; //TOP分配给应用的AppKey
	        _this.session = null; //用户登录授权成功后，TOP颁发给应用的授权信息，非必填
	        _this.format = "xml"; //响应格式。默认为xml格式，可选值：xml，json
	        _this.v = "2.0"; //API协议版本
	        _this.sign_method = "md5"; //签名的摘要算法，可选值为：hmac，md5
	        //处理请求环境
	        var serverType = topConfig.env[0], httpType = topConfig.env[1];
	        _this.REST_URL = topRestConfig[serverType][httpType];
	        _this.app_key = topConfig.app_key;
	        _this.appsecret = topConfig.appsecret;
	        _this.session = topConfig.session ? topConfig.session : null;
	        return _this;
	    }
	    TOPClient.prototype.connect = function (url, ajaxType, isUpload, data) {
	        if (isUpload === void 0) { isUpload = false; }
	        console.log("开启TOP连接...数据处理中");
	        //根据Top规范，将接口API变为method参数
	        data["method"] = this.method = url;
	        //修改请求链接为服务器地址 
	        url = this.REST_URL;
	        //处理请求数据
	        this.formatParams(data);
	        // return super.connect(url, this.params);
	        return _super.prototype.connect.call(this, this.createUrl(), ajaxType, isUpload);
	    };
	    TOPClient.prototype.formatParams = function (operationalParams) {
	        this.params = {
	            app_key: this.app_key,
	            format: this.format,
	            timestamp: this.getTime(),
	            method: this.method,
	            sign_method: this.sign_method,
	            v: this.v
	        };
	        if (this.session) {
	            this.params.session = this.session;
	        }
	        for (var k in operationalParams) {
	            if (k == "appsecret") {
	                this.appsecret = operationalParams[k];
	            }
	            else {
	                this.params[k] = operationalParams[k];
	            }
	        }
	        this.params.sign = this.getSign(this.params);
	    };
	    TOPClient.prototype.createUrl = function () {
	        var p_array = [], p_str = "";
	        for (var k in this.params) {
	            var value = this.params[k];
	            p_array.push(k + '=' + encodeURIComponent(value));
	        }
	        p_str = p_array.join("&");
	        var request_url = this.REST_URL + '?' + p_str;
	        console.log(request_url);
	        return request_url;
	    };
	    /**
	     * 获取时间戳——当前时间
	     */
	    TOPClient.prototype.getTime = function () {
	        var now = new Date(), yyyy = now.getFullYear().toString(), MM = (now.getMonth() + 1) > 9 ? (now.getMonth() + 1).toString() : "0" + (now.getMonth() + 1).toString(), dd = now.getDate() > 9 ? now.getDate().toString() : "0" + now.getDate().toString(), HH = now.getHours() > 9 ? now.getHours().toString() : "0" + now.getHours().toString(), mm = now.getMinutes() > 9 ? now.getMinutes().toString() : "0" + now.getMinutes().toString(), ss = now.getSeconds() > 9 ? now.getSeconds().toString() : "0" + now.getSeconds().toString();
	        return yyyy + '-' + MM + '-' + dd + ' ' + HH + ':' + mm + ':' + ss;
	    };
	    /**
	     * 加密处理
	     * @params type {string} 加密算法类型：md5或hmac
	     * @params msg {string} 加密信息
	     * @params secret {string} 加密密钥
	     */
	    TOPClient.prototype.encrypt = function (type, msg, secret) {
	        var result = "";
	        switch (type) {
	            case "md5":
	                result = CryptoJS.MD5(secret + msg + secret).toString().toUpperCase();
	                break;
	            case "hmac":
	                result = CryptoJS.HmacMD5(msg, secret).toString().toUpperCase();
	                break;
	            default:
	                result = CryptoJS.MD5(secret + msg + secret).toString().toUpperCase();
	                break;
	        }
	        return result;
	    };
	    /**
	     * 获取API输入参数签名结果
	     * @params params {ITopParams} 除sign外需要传参的对象
	     */
	    TOPClient.prototype.getSign = function (params) {
	        var sortParam = function (params) {
	            var sort = "", key_array = [];
	            for (var k in params) {
	                key_array.push(k);
	            }
	            key_array.sort();
	            key_array.forEach(function (element) {
	                if (params[element]) {
	                    sort += element.toString() + params[element];
	                }
	            });
	            // console.log(sort);
	            return sort;
	        };
	        var msg = sortParam(params), new_sign = this.encrypt(this.sign_method, msg, this.appsecret);
	        this.sign = new_sign;
	        return new_sign;
	    };
	    return TOPClient;
	}(ClientFactory_1.ClientFactory));
	exports.default = TOPClient;


/***/ }),
/* 100 */
/***/ (function(module, exports) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	var ClientFactory = (function () {
	    function ClientFactory(cumsData) {
	        this._data = cumsData;
	    }
	    ClientFactory.prototype.connect = function (url, ajaxType, isUpload, data) {
	        console.log("连接开始...");
	        var promise = new Promise(function (resolve, reject) {
	            //创建
	            var client = createxmlHttpRequest();
	            if (!client) {
	                alert("您的浏览器不支持AJAX！");
	                return 0;
	            }
	            //接收
	            client.onreadystatechange = handler;
	            client.responseType = "json";
	            //连接 和 发送
	            var params = formatParams(data);
	            //添加_tb_token_
	            var _tb_token_ = "";
	            if (document.cookie.match(/_tb_token_\s*=\s*[^;]+/)) {
	                _tb_token_ = document.cookie.match(/_tb_token_\s*=\s*[^;]+/)[0].replace(/\s/g, '').split('=')[1];
	            }
	            params = params + "&_tb_token_=" + _tb_token_;
	            if (isUpload) {
	                client.open(ajaxType, url, true);
	                client.send(data);
	            }
	            else {
	                if (ajaxType == 'post' || ajaxType == 'POST') {
	                    client.open(ajaxType, url, true);
	                    client.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	                    client.send(params);
	                }
	                else if (ajaxType == 'get' || ajaxType == 'GET') {
	                    client.open(ajaxType, url + "?" + params, true);
	                    client.send();
	                }
	            }
	            //接收
	            function handler() {
	                if (client.readyState === 4) {
	                    if (client.status === 200) {
	                        resolve(client.response);
	                    }
	                    else {
	                        reject(new Error(client.statusText));
	                    }
	                }
	            }
	            ;
	            function createxmlHttpRequest() {
	                var client;
	                try {
	                    //Firefox, Opera 8.0+, Safari
	                    client = new XMLHttpRequest();
	                }
	                catch (e) {
	                    //IE
	                    try {
	                        client = new ActiveXObject("Msxml2.XMLHTTP");
	                    }
	                    catch (e) {
	                        client = new ActiveXObject("Microsoft.XMLHTTP");
	                    }
	                }
	                return client;
	            }
	            //格式化参数
	            function formatParams(data) {
	                if (typeof data === 'object') {
	                    var arr = [];
	                    for (var name in data) {
	                        arr.push(encodeURIComponent(name) + "=" + encodeURIComponent(data[name]));
	                    }
	                    return arr.join("&");
	                }
	                else {
	                    return data;
	                }
	            }
	        });
	        return promise;
	    };
	    return ClientFactory;
	}());
	exports.ClientFactory = ClientFactory;


/***/ }),
/* 101 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(103), __webpack_require__(104), __webpack_require__(105), __webpack_require__(106), __webpack_require__(107), __webpack_require__(108), __webpack_require__(109), __webpack_require__(110), __webpack_require__(111), __webpack_require__(112), __webpack_require__(113), __webpack_require__(114), __webpack_require__(115), __webpack_require__(116), __webpack_require__(117), __webpack_require__(118), __webpack_require__(119), __webpack_require__(120), __webpack_require__(121), __webpack_require__(122), __webpack_require__(123), __webpack_require__(124), __webpack_require__(125), __webpack_require__(126), __webpack_require__(127), __webpack_require__(128), __webpack_require__(129), __webpack_require__(130), __webpack_require__(131), __webpack_require__(132), __webpack_require__(133), __webpack_require__(134));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./x64-core", "./lib-typedarrays", "./enc-utf16", "./enc-base64", "./md5", "./sha1", "./sha256", "./sha224", "./sha512", "./sha384", "./sha3", "./ripemd160", "./hmac", "./pbkdf2", "./evpkdf", "./cipher-core", "./mode-cfb", "./mode-ctr", "./mode-ctr-gladman", "./mode-ofb", "./mode-ecb", "./pad-ansix923", "./pad-iso10126", "./pad-iso97971", "./pad-zeropadding", "./pad-nopadding", "./format-hex", "./aes", "./tripledes", "./rc4", "./rabbit", "./rabbit-legacy"], factory);
		}
		else {
			// Global (browser)
			root.CryptoJS = factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		return CryptoJS;
	
	}));

/***/ }),
/* 102 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory) {
		if (true) {
			// CommonJS
			module.exports = exports = factory();
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define([], factory);
		}
		else {
			// Global (browser)
			root.CryptoJS = factory();
		}
	}(this, function () {
	
		/**
		 * CryptoJS core components.
		 */
		var CryptoJS = CryptoJS || (function (Math, undefined) {
		    /*
		     * Local polyfil of Object.create
		     */
		    var create = Object.create || (function () {
		        function F() {};
	
		        return function (obj) {
		            var subtype;
	
		            F.prototype = obj;
	
		            subtype = new F();
	
		            F.prototype = null;
	
		            return subtype;
		        };
		    }())
	
		    /**
		     * CryptoJS namespace.
		     */
		    var C = {};
	
		    /**
		     * Library namespace.
		     */
		    var C_lib = C.lib = {};
	
		    /**
		     * Base object for prototypal inheritance.
		     */
		    var Base = C_lib.Base = (function () {
	
	
		        return {
		            /**
		             * Creates a new object that inherits from this object.
		             *
		             * @param {Object} overrides Properties to copy into the new object.
		             *
		             * @return {Object} The new object.
		             *
		             * @static
		             *
		             * @example
		             *
		             *     var MyType = CryptoJS.lib.Base.extend({
		             *         field: 'value',
		             *
		             *         method: function () {
		             *         }
		             *     });
		             */
		            extend: function (overrides) {
		                // Spawn
		                var subtype = create(this);
	
		                // Augment
		                if (overrides) {
		                    subtype.mixIn(overrides);
		                }
	
		                // Create default initializer
		                if (!subtype.hasOwnProperty('init') || this.init === subtype.init) {
		                    subtype.init = function () {
		                        subtype.$super.init.apply(this, arguments);
		                    };
		                }
	
		                // Initializer's prototype is the subtype object
		                subtype.init.prototype = subtype;
	
		                // Reference supertype
		                subtype.$super = this;
	
		                return subtype;
		            },
	
		            /**
		             * Extends this object and runs the init method.
		             * Arguments to create() will be passed to init().
		             *
		             * @return {Object} The new object.
		             *
		             * @static
		             *
		             * @example
		             *
		             *     var instance = MyType.create();
		             */
		            create: function () {
		                var instance = this.extend();
		                instance.init.apply(instance, arguments);
	
		                return instance;
		            },
	
		            /**
		             * Initializes a newly created object.
		             * Override this method to add some logic when your objects are created.
		             *
		             * @example
		             *
		             *     var MyType = CryptoJS.lib.Base.extend({
		             *         init: function () {
		             *             // ...
		             *         }
		             *     });
		             */
		            init: function () {
		            },
	
		            /**
		             * Copies properties into this object.
		             *
		             * @param {Object} properties The properties to mix in.
		             *
		             * @example
		             *
		             *     MyType.mixIn({
		             *         field: 'value'
		             *     });
		             */
		            mixIn: function (properties) {
		                for (var propertyName in properties) {
		                    if (properties.hasOwnProperty(propertyName)) {
		                        this[propertyName] = properties[propertyName];
		                    }
		                }
	
		                // IE won't copy toString using the loop above
		                if (properties.hasOwnProperty('toString')) {
		                    this.toString = properties.toString;
		                }
		            },
	
		            /**
		             * Creates a copy of this object.
		             *
		             * @return {Object} The clone.
		             *
		             * @example
		             *
		             *     var clone = instance.clone();
		             */
		            clone: function () {
		                return this.init.prototype.extend(this);
		            }
		        };
		    }());
	
		    /**
		     * An array of 32-bit words.
		     *
		     * @property {Array} words The array of 32-bit words.
		     * @property {number} sigBytes The number of significant bytes in this word array.
		     */
		    var WordArray = C_lib.WordArray = Base.extend({
		        /**
		         * Initializes a newly created word array.
		         *
		         * @param {Array} words (Optional) An array of 32-bit words.
		         * @param {number} sigBytes (Optional) The number of significant bytes in the words.
		         *
		         * @example
		         *
		         *     var wordArray = CryptoJS.lib.WordArray.create();
		         *     var wordArray = CryptoJS.lib.WordArray.create([0x00010203, 0x04050607]);
		         *     var wordArray = CryptoJS.lib.WordArray.create([0x00010203, 0x04050607], 6);
		         */
		        init: function (words, sigBytes) {
		            words = this.words = words || [];
	
		            if (sigBytes != undefined) {
		                this.sigBytes = sigBytes;
		            } else {
		                this.sigBytes = words.length * 4;
		            }
		        },
	
		        /**
		         * Converts this word array to a string.
		         *
		         * @param {Encoder} encoder (Optional) The encoding strategy to use. Default: CryptoJS.enc.Hex
		         *
		         * @return {string} The stringified word array.
		         *
		         * @example
		         *
		         *     var string = wordArray + '';
		         *     var string = wordArray.toString();
		         *     var string = wordArray.toString(CryptoJS.enc.Utf8);
		         */
		        toString: function (encoder) {
		            return (encoder || Hex).stringify(this);
		        },
	
		        /**
		         * Concatenates a word array to this word array.
		         *
		         * @param {WordArray} wordArray The word array to append.
		         *
		         * @return {WordArray} This word array.
		         *
		         * @example
		         *
		         *     wordArray1.concat(wordArray2);
		         */
		        concat: function (wordArray) {
		            // Shortcuts
		            var thisWords = this.words;
		            var thatWords = wordArray.words;
		            var thisSigBytes = this.sigBytes;
		            var thatSigBytes = wordArray.sigBytes;
	
		            // Clamp excess bits
		            this.clamp();
	
		            // Concat
		            if (thisSigBytes % 4) {
		                // Copy one byte at a time
		                for (var i = 0; i < thatSigBytes; i++) {
		                    var thatByte = (thatWords[i >>> 2] >>> (24 - (i % 4) * 8)) & 0xff;
		                    thisWords[(thisSigBytes + i) >>> 2] |= thatByte << (24 - ((thisSigBytes + i) % 4) * 8);
		                }
		            } else {
		                // Copy one word at a time
		                for (var i = 0; i < thatSigBytes; i += 4) {
		                    thisWords[(thisSigBytes + i) >>> 2] = thatWords[i >>> 2];
		                }
		            }
		            this.sigBytes += thatSigBytes;
	
		            // Chainable
		            return this;
		        },
	
		        /**
		         * Removes insignificant bits.
		         *
		         * @example
		         *
		         *     wordArray.clamp();
		         */
		        clamp: function () {
		            // Shortcuts
		            var words = this.words;
		            var sigBytes = this.sigBytes;
	
		            // Clamp
		            words[sigBytes >>> 2] &= 0xffffffff << (32 - (sigBytes % 4) * 8);
		            words.length = Math.ceil(sigBytes / 4);
		        },
	
		        /**
		         * Creates a copy of this word array.
		         *
		         * @return {WordArray} The clone.
		         *
		         * @example
		         *
		         *     var clone = wordArray.clone();
		         */
		        clone: function () {
		            var clone = Base.clone.call(this);
		            clone.words = this.words.slice(0);
	
		            return clone;
		        },
	
		        /**
		         * Creates a word array filled with random bytes.
		         *
		         * @param {number} nBytes The number of random bytes to generate.
		         *
		         * @return {WordArray} The random word array.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var wordArray = CryptoJS.lib.WordArray.random(16);
		         */
		        random: function (nBytes) {
		            var words = [];
	
		            var r = (function (m_w) {
		                var m_w = m_w;
		                var m_z = 0x3ade68b1;
		                var mask = 0xffffffff;
	
		                return function () {
		                    m_z = (0x9069 * (m_z & 0xFFFF) + (m_z >> 0x10)) & mask;
		                    m_w = (0x4650 * (m_w & 0xFFFF) + (m_w >> 0x10)) & mask;
		                    var result = ((m_z << 0x10) + m_w) & mask;
		                    result /= 0x100000000;
		                    result += 0.5;
		                    return result * (Math.random() > .5 ? 1 : -1);
		                }
		            });
	
		            for (var i = 0, rcache; i < nBytes; i += 4) {
		                var _r = r((rcache || Math.random()) * 0x100000000);
	
		                rcache = _r() * 0x3ade67b7;
		                words.push((_r() * 0x100000000) | 0);
		            }
	
		            return new WordArray.init(words, nBytes);
		        }
		    });
	
		    /**
		     * Encoder namespace.
		     */
		    var C_enc = C.enc = {};
	
		    /**
		     * Hex encoding strategy.
		     */
		    var Hex = C_enc.Hex = {
		        /**
		         * Converts a word array to a hex string.
		         *
		         * @param {WordArray} wordArray The word array.
		         *
		         * @return {string} The hex string.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var hexString = CryptoJS.enc.Hex.stringify(wordArray);
		         */
		        stringify: function (wordArray) {
		            // Shortcuts
		            var words = wordArray.words;
		            var sigBytes = wordArray.sigBytes;
	
		            // Convert
		            var hexChars = [];
		            for (var i = 0; i < sigBytes; i++) {
		                var bite = (words[i >>> 2] >>> (24 - (i % 4) * 8)) & 0xff;
		                hexChars.push((bite >>> 4).toString(16));
		                hexChars.push((bite & 0x0f).toString(16));
		            }
	
		            return hexChars.join('');
		        },
	
		        /**
		         * Converts a hex string to a word array.
		         *
		         * @param {string} hexStr The hex string.
		         *
		         * @return {WordArray} The word array.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var wordArray = CryptoJS.enc.Hex.parse(hexString);
		         */
		        parse: function (hexStr) {
		            // Shortcut
		            var hexStrLength = hexStr.length;
	
		            // Convert
		            var words = [];
		            for (var i = 0; i < hexStrLength; i += 2) {
		                words[i >>> 3] |= parseInt(hexStr.substr(i, 2), 16) << (24 - (i % 8) * 4);
		            }
	
		            return new WordArray.init(words, hexStrLength / 2);
		        }
		    };
	
		    /**
		     * Latin1 encoding strategy.
		     */
		    var Latin1 = C_enc.Latin1 = {
		        /**
		         * Converts a word array to a Latin1 string.
		         *
		         * @param {WordArray} wordArray The word array.
		         *
		         * @return {string} The Latin1 string.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var latin1String = CryptoJS.enc.Latin1.stringify(wordArray);
		         */
		        stringify: function (wordArray) {
		            // Shortcuts
		            var words = wordArray.words;
		            var sigBytes = wordArray.sigBytes;
	
		            // Convert
		            var latin1Chars = [];
		            for (var i = 0; i < sigBytes; i++) {
		                var bite = (words[i >>> 2] >>> (24 - (i % 4) * 8)) & 0xff;
		                latin1Chars.push(String.fromCharCode(bite));
		            }
	
		            return latin1Chars.join('');
		        },
	
		        /**
		         * Converts a Latin1 string to a word array.
		         *
		         * @param {string} latin1Str The Latin1 string.
		         *
		         * @return {WordArray} The word array.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var wordArray = CryptoJS.enc.Latin1.parse(latin1String);
		         */
		        parse: function (latin1Str) {
		            // Shortcut
		            var latin1StrLength = latin1Str.length;
	
		            // Convert
		            var words = [];
		            for (var i = 0; i < latin1StrLength; i++) {
		                words[i >>> 2] |= (latin1Str.charCodeAt(i) & 0xff) << (24 - (i % 4) * 8);
		            }
	
		            return new WordArray.init(words, latin1StrLength);
		        }
		    };
	
		    /**
		     * UTF-8 encoding strategy.
		     */
		    var Utf8 = C_enc.Utf8 = {
		        /**
		         * Converts a word array to a UTF-8 string.
		         *
		         * @param {WordArray} wordArray The word array.
		         *
		         * @return {string} The UTF-8 string.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var utf8String = CryptoJS.enc.Utf8.stringify(wordArray);
		         */
		        stringify: function (wordArray) {
		            try {
		                return decodeURIComponent(escape(Latin1.stringify(wordArray)));
		            } catch (e) {
		                throw new Error('Malformed UTF-8 data');
		            }
		        },
	
		        /**
		         * Converts a UTF-8 string to a word array.
		         *
		         * @param {string} utf8Str The UTF-8 string.
		         *
		         * @return {WordArray} The word array.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var wordArray = CryptoJS.enc.Utf8.parse(utf8String);
		         */
		        parse: function (utf8Str) {
		            return Latin1.parse(unescape(encodeURIComponent(utf8Str)));
		        }
		    };
	
		    /**
		     * Abstract buffered block algorithm template.
		     *
		     * The property blockSize must be implemented in a concrete subtype.
		     *
		     * @property {number} _minBufferSize The number of blocks that should be kept unprocessed in the buffer. Default: 0
		     */
		    var BufferedBlockAlgorithm = C_lib.BufferedBlockAlgorithm = Base.extend({
		        /**
		         * Resets this block algorithm's data buffer to its initial state.
		         *
		         * @example
		         *
		         *     bufferedBlockAlgorithm.reset();
		         */
		        reset: function () {
		            // Initial values
		            this._data = new WordArray.init();
		            this._nDataBytes = 0;
		        },
	
		        /**
		         * Adds new data to this block algorithm's buffer.
		         *
		         * @param {WordArray|string} data The data to append. Strings are converted to a WordArray using UTF-8.
		         *
		         * @example
		         *
		         *     bufferedBlockAlgorithm._append('data');
		         *     bufferedBlockAlgorithm._append(wordArray);
		         */
		        _append: function (data) {
		            // Convert string to WordArray, else assume WordArray already
		            if (typeof data == 'string') {
		                data = Utf8.parse(data);
		            }
	
		            // Append
		            this._data.concat(data);
		            this._nDataBytes += data.sigBytes;
		        },
	
		        /**
		         * Processes available data blocks.
		         *
		         * This method invokes _doProcessBlock(offset), which must be implemented by a concrete subtype.
		         *
		         * @param {boolean} doFlush Whether all blocks and partial blocks should be processed.
		         *
		         * @return {WordArray} The processed data.
		         *
		         * @example
		         *
		         *     var processedData = bufferedBlockAlgorithm._process();
		         *     var processedData = bufferedBlockAlgorithm._process(!!'flush');
		         */
		        _process: function (doFlush) {
		            // Shortcuts
		            var data = this._data;
		            var dataWords = data.words;
		            var dataSigBytes = data.sigBytes;
		            var blockSize = this.blockSize;
		            var blockSizeBytes = blockSize * 4;
	
		            // Count blocks ready
		            var nBlocksReady = dataSigBytes / blockSizeBytes;
		            if (doFlush) {
		                // Round up to include partial blocks
		                nBlocksReady = Math.ceil(nBlocksReady);
		            } else {
		                // Round down to include only full blocks,
		                // less the number of blocks that must remain in the buffer
		                nBlocksReady = Math.max((nBlocksReady | 0) - this._minBufferSize, 0);
		            }
	
		            // Count words ready
		            var nWordsReady = nBlocksReady * blockSize;
	
		            // Count bytes ready
		            var nBytesReady = Math.min(nWordsReady * 4, dataSigBytes);
	
		            // Process blocks
		            if (nWordsReady) {
		                for (var offset = 0; offset < nWordsReady; offset += blockSize) {
		                    // Perform concrete-algorithm logic
		                    this._doProcessBlock(dataWords, offset);
		                }
	
		                // Remove processed words
		                var processedWords = dataWords.splice(0, nWordsReady);
		                data.sigBytes -= nBytesReady;
		            }
	
		            // Return processed words
		            return new WordArray.init(processedWords, nBytesReady);
		        },
	
		        /**
		         * Creates a copy of this object.
		         *
		         * @return {Object} The clone.
		         *
		         * @example
		         *
		         *     var clone = bufferedBlockAlgorithm.clone();
		         */
		        clone: function () {
		            var clone = Base.clone.call(this);
		            clone._data = this._data.clone();
	
		            return clone;
		        },
	
		        _minBufferSize: 0
		    });
	
		    /**
		     * Abstract hasher template.
		     *
		     * @property {number} blockSize The number of 32-bit words this hasher operates on. Default: 16 (512 bits)
		     */
		    var Hasher = C_lib.Hasher = BufferedBlockAlgorithm.extend({
		        /**
		         * Configuration options.
		         */
		        cfg: Base.extend(),
	
		        /**
		         * Initializes a newly created hasher.
		         *
		         * @param {Object} cfg (Optional) The configuration options to use for this hash computation.
		         *
		         * @example
		         *
		         *     var hasher = CryptoJS.algo.SHA256.create();
		         */
		        init: function (cfg) {
		            // Apply config defaults
		            this.cfg = this.cfg.extend(cfg);
	
		            // Set initial values
		            this.reset();
		        },
	
		        /**
		         * Resets this hasher to its initial state.
		         *
		         * @example
		         *
		         *     hasher.reset();
		         */
		        reset: function () {
		            // Reset data buffer
		            BufferedBlockAlgorithm.reset.call(this);
	
		            // Perform concrete-hasher logic
		            this._doReset();
		        },
	
		        /**
		         * Updates this hasher with a message.
		         *
		         * @param {WordArray|string} messageUpdate The message to append.
		         *
		         * @return {Hasher} This hasher.
		         *
		         * @example
		         *
		         *     hasher.update('message');
		         *     hasher.update(wordArray);
		         */
		        update: function (messageUpdate) {
		            // Append
		            this._append(messageUpdate);
	
		            // Update the hash
		            this._process();
	
		            // Chainable
		            return this;
		        },
	
		        /**
		         * Finalizes the hash computation.
		         * Note that the finalize operation is effectively a destructive, read-once operation.
		         *
		         * @param {WordArray|string} messageUpdate (Optional) A final message update.
		         *
		         * @return {WordArray} The hash.
		         *
		         * @example
		         *
		         *     var hash = hasher.finalize();
		         *     var hash = hasher.finalize('message');
		         *     var hash = hasher.finalize(wordArray);
		         */
		        finalize: function (messageUpdate) {
		            // Final message update
		            if (messageUpdate) {
		                this._append(messageUpdate);
		            }
	
		            // Perform concrete-hasher logic
		            var hash = this._doFinalize();
	
		            return hash;
		        },
	
		        blockSize: 512/32,
	
		        /**
		         * Creates a shortcut function to a hasher's object interface.
		         *
		         * @param {Hasher} hasher The hasher to create a helper for.
		         *
		         * @return {Function} The shortcut function.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var SHA256 = CryptoJS.lib.Hasher._createHelper(CryptoJS.algo.SHA256);
		         */
		        _createHelper: function (hasher) {
		            return function (message, cfg) {
		                return new hasher.init(cfg).finalize(message);
		            };
		        },
	
		        /**
		         * Creates a shortcut function to the HMAC's object interface.
		         *
		         * @param {Hasher} hasher The hasher to use in this HMAC helper.
		         *
		         * @return {Function} The shortcut function.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var HmacSHA256 = CryptoJS.lib.Hasher._createHmacHelper(CryptoJS.algo.SHA256);
		         */
		        _createHmacHelper: function (hasher) {
		            return function (message, key) {
		                return new C_algo.HMAC.init(hasher, key).finalize(message);
		            };
		        }
		    });
	
		    /**
		     * Algorithm namespace.
		     */
		    var C_algo = C.algo = {};
	
		    return C;
		}(Math));
	
	
		return CryptoJS;
	
	}));

/***/ }),
/* 103 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		(function (undefined) {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var Base = C_lib.Base;
		    var X32WordArray = C_lib.WordArray;
	
		    /**
		     * x64 namespace.
		     */
		    var C_x64 = C.x64 = {};
	
		    /**
		     * A 64-bit word.
		     */
		    var X64Word = C_x64.Word = Base.extend({
		        /**
		         * Initializes a newly created 64-bit word.
		         *
		         * @param {number} high The high 32 bits.
		         * @param {number} low The low 32 bits.
		         *
		         * @example
		         *
		         *     var x64Word = CryptoJS.x64.Word.create(0x00010203, 0x04050607);
		         */
		        init: function (high, low) {
		            this.high = high;
		            this.low = low;
		        }
	
		        /**
		         * Bitwise NOTs this word.
		         *
		         * @return {X64Word} A new x64-Word object after negating.
		         *
		         * @example
		         *
		         *     var negated = x64Word.not();
		         */
		        // not: function () {
		            // var high = ~this.high;
		            // var low = ~this.low;
	
		            // return X64Word.create(high, low);
		        // },
	
		        /**
		         * Bitwise ANDs this word with the passed word.
		         *
		         * @param {X64Word} word The x64-Word to AND with this word.
		         *
		         * @return {X64Word} A new x64-Word object after ANDing.
		         *
		         * @example
		         *
		         *     var anded = x64Word.and(anotherX64Word);
		         */
		        // and: function (word) {
		            // var high = this.high & word.high;
		            // var low = this.low & word.low;
	
		            // return X64Word.create(high, low);
		        // },
	
		        /**
		         * Bitwise ORs this word with the passed word.
		         *
		         * @param {X64Word} word The x64-Word to OR with this word.
		         *
		         * @return {X64Word} A new x64-Word object after ORing.
		         *
		         * @example
		         *
		         *     var ored = x64Word.or(anotherX64Word);
		         */
		        // or: function (word) {
		            // var high = this.high | word.high;
		            // var low = this.low | word.low;
	
		            // return X64Word.create(high, low);
		        // },
	
		        /**
		         * Bitwise XORs this word with the passed word.
		         *
		         * @param {X64Word} word The x64-Word to XOR with this word.
		         *
		         * @return {X64Word} A new x64-Word object after XORing.
		         *
		         * @example
		         *
		         *     var xored = x64Word.xor(anotherX64Word);
		         */
		        // xor: function (word) {
		            // var high = this.high ^ word.high;
		            // var low = this.low ^ word.low;
	
		            // return X64Word.create(high, low);
		        // },
	
		        /**
		         * Shifts this word n bits to the left.
		         *
		         * @param {number} n The number of bits to shift.
		         *
		         * @return {X64Word} A new x64-Word object after shifting.
		         *
		         * @example
		         *
		         *     var shifted = x64Word.shiftL(25);
		         */
		        // shiftL: function (n) {
		            // if (n < 32) {
		                // var high = (this.high << n) | (this.low >>> (32 - n));
		                // var low = this.low << n;
		            // } else {
		                // var high = this.low << (n - 32);
		                // var low = 0;
		            // }
	
		            // return X64Word.create(high, low);
		        // },
	
		        /**
		         * Shifts this word n bits to the right.
		         *
		         * @param {number} n The number of bits to shift.
		         *
		         * @return {X64Word} A new x64-Word object after shifting.
		         *
		         * @example
		         *
		         *     var shifted = x64Word.shiftR(7);
		         */
		        // shiftR: function (n) {
		            // if (n < 32) {
		                // var low = (this.low >>> n) | (this.high << (32 - n));
		                // var high = this.high >>> n;
		            // } else {
		                // var low = this.high >>> (n - 32);
		                // var high = 0;
		            // }
	
		            // return X64Word.create(high, low);
		        // },
	
		        /**
		         * Rotates this word n bits to the left.
		         *
		         * @param {number} n The number of bits to rotate.
		         *
		         * @return {X64Word} A new x64-Word object after rotating.
		         *
		         * @example
		         *
		         *     var rotated = x64Word.rotL(25);
		         */
		        // rotL: function (n) {
		            // return this.shiftL(n).or(this.shiftR(64 - n));
		        // },
	
		        /**
		         * Rotates this word n bits to the right.
		         *
		         * @param {number} n The number of bits to rotate.
		         *
		         * @return {X64Word} A new x64-Word object after rotating.
		         *
		         * @example
		         *
		         *     var rotated = x64Word.rotR(7);
		         */
		        // rotR: function (n) {
		            // return this.shiftR(n).or(this.shiftL(64 - n));
		        // },
	
		        /**
		         * Adds this word with the passed word.
		         *
		         * @param {X64Word} word The x64-Word to add with this word.
		         *
		         * @return {X64Word} A new x64-Word object after adding.
		         *
		         * @example
		         *
		         *     var added = x64Word.add(anotherX64Word);
		         */
		        // add: function (word) {
		            // var low = (this.low + word.low) | 0;
		            // var carry = (low >>> 0) < (this.low >>> 0) ? 1 : 0;
		            // var high = (this.high + word.high + carry) | 0;
	
		            // return X64Word.create(high, low);
		        // }
		    });
	
		    /**
		     * An array of 64-bit words.
		     *
		     * @property {Array} words The array of CryptoJS.x64.Word objects.
		     * @property {number} sigBytes The number of significant bytes in this word array.
		     */
		    var X64WordArray = C_x64.WordArray = Base.extend({
		        /**
		         * Initializes a newly created word array.
		         *
		         * @param {Array} words (Optional) An array of CryptoJS.x64.Word objects.
		         * @param {number} sigBytes (Optional) The number of significant bytes in the words.
		         *
		         * @example
		         *
		         *     var wordArray = CryptoJS.x64.WordArray.create();
		         *
		         *     var wordArray = CryptoJS.x64.WordArray.create([
		         *         CryptoJS.x64.Word.create(0x00010203, 0x04050607),
		         *         CryptoJS.x64.Word.create(0x18191a1b, 0x1c1d1e1f)
		         *     ]);
		         *
		         *     var wordArray = CryptoJS.x64.WordArray.create([
		         *         CryptoJS.x64.Word.create(0x00010203, 0x04050607),
		         *         CryptoJS.x64.Word.create(0x18191a1b, 0x1c1d1e1f)
		         *     ], 10);
		         */
		        init: function (words, sigBytes) {
		            words = this.words = words || [];
	
		            if (sigBytes != undefined) {
		                this.sigBytes = sigBytes;
		            } else {
		                this.sigBytes = words.length * 8;
		            }
		        },
	
		        /**
		         * Converts this 64-bit word array to a 32-bit word array.
		         *
		         * @return {CryptoJS.lib.WordArray} This word array's data as a 32-bit word array.
		         *
		         * @example
		         *
		         *     var x32WordArray = x64WordArray.toX32();
		         */
		        toX32: function () {
		            // Shortcuts
		            var x64Words = this.words;
		            var x64WordsLength = x64Words.length;
	
		            // Convert
		            var x32Words = [];
		            for (var i = 0; i < x64WordsLength; i++) {
		                var x64Word = x64Words[i];
		                x32Words.push(x64Word.high);
		                x32Words.push(x64Word.low);
		            }
	
		            return X32WordArray.create(x32Words, this.sigBytes);
		        },
	
		        /**
		         * Creates a copy of this word array.
		         *
		         * @return {X64WordArray} The clone.
		         *
		         * @example
		         *
		         *     var clone = x64WordArray.clone();
		         */
		        clone: function () {
		            var clone = Base.clone.call(this);
	
		            // Clone "words" array
		            var words = clone.words = this.words.slice(0);
	
		            // Clone each X64Word object
		            var wordsLength = words.length;
		            for (var i = 0; i < wordsLength; i++) {
		                words[i] = words[i].clone();
		            }
	
		            return clone;
		        }
		    });
		}());
	
	
		return CryptoJS;
	
	}));

/***/ }),
/* 104 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		(function () {
		    // Check if typed arrays are supported
		    if (typeof ArrayBuffer != 'function') {
		        return;
		    }
	
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var WordArray = C_lib.WordArray;
	
		    // Reference original init
		    var superInit = WordArray.init;
	
		    // Augment WordArray.init to handle typed arrays
		    var subInit = WordArray.init = function (typedArray) {
		        // Convert buffers to uint8
		        if (typedArray instanceof ArrayBuffer) {
		            typedArray = new Uint8Array(typedArray);
		        }
	
		        // Convert other array views to uint8
		        if (
		            typedArray instanceof Int8Array ||
		            (typeof Uint8ClampedArray !== "undefined" && typedArray instanceof Uint8ClampedArray) ||
		            typedArray instanceof Int16Array ||
		            typedArray instanceof Uint16Array ||
		            typedArray instanceof Int32Array ||
		            typedArray instanceof Uint32Array ||
		            typedArray instanceof Float32Array ||
		            typedArray instanceof Float64Array
		        ) {
		            typedArray = new Uint8Array(typedArray.buffer, typedArray.byteOffset, typedArray.byteLength);
		        }
	
		        // Handle Uint8Array
		        if (typedArray instanceof Uint8Array) {
		            // Shortcut
		            var typedArrayByteLength = typedArray.byteLength;
	
		            // Extract bytes
		            var words = [];
		            for (var i = 0; i < typedArrayByteLength; i++) {
		                words[i >>> 2] |= typedArray[i] << (24 - (i % 4) * 8);
		            }
	
		            // Initialize this word array
		            superInit.call(this, words, typedArrayByteLength);
		        } else {
		            // Else call normal init
		            superInit.apply(this, arguments);
		        }
		    };
	
		    subInit.prototype = WordArray;
		}());
	
	
		return CryptoJS.lib.WordArray;
	
	}));

/***/ }),
/* 105 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		(function () {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var WordArray = C_lib.WordArray;
		    var C_enc = C.enc;
	
		    /**
		     * UTF-16 BE encoding strategy.
		     */
		    var Utf16BE = C_enc.Utf16 = C_enc.Utf16BE = {
		        /**
		         * Converts a word array to a UTF-16 BE string.
		         *
		         * @param {WordArray} wordArray The word array.
		         *
		         * @return {string} The UTF-16 BE string.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var utf16String = CryptoJS.enc.Utf16.stringify(wordArray);
		         */
		        stringify: function (wordArray) {
		            // Shortcuts
		            var words = wordArray.words;
		            var sigBytes = wordArray.sigBytes;
	
		            // Convert
		            var utf16Chars = [];
		            for (var i = 0; i < sigBytes; i += 2) {
		                var codePoint = (words[i >>> 2] >>> (16 - (i % 4) * 8)) & 0xffff;
		                utf16Chars.push(String.fromCharCode(codePoint));
		            }
	
		            return utf16Chars.join('');
		        },
	
		        /**
		         * Converts a UTF-16 BE string to a word array.
		         *
		         * @param {string} utf16Str The UTF-16 BE string.
		         *
		         * @return {WordArray} The word array.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var wordArray = CryptoJS.enc.Utf16.parse(utf16String);
		         */
		        parse: function (utf16Str) {
		            // Shortcut
		            var utf16StrLength = utf16Str.length;
	
		            // Convert
		            var words = [];
		            for (var i = 0; i < utf16StrLength; i++) {
		                words[i >>> 1] |= utf16Str.charCodeAt(i) << (16 - (i % 2) * 16);
		            }
	
		            return WordArray.create(words, utf16StrLength * 2);
		        }
		    };
	
		    /**
		     * UTF-16 LE encoding strategy.
		     */
		    C_enc.Utf16LE = {
		        /**
		         * Converts a word array to a UTF-16 LE string.
		         *
		         * @param {WordArray} wordArray The word array.
		         *
		         * @return {string} The UTF-16 LE string.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var utf16Str = CryptoJS.enc.Utf16LE.stringify(wordArray);
		         */
		        stringify: function (wordArray) {
		            // Shortcuts
		            var words = wordArray.words;
		            var sigBytes = wordArray.sigBytes;
	
		            // Convert
		            var utf16Chars = [];
		            for (var i = 0; i < sigBytes; i += 2) {
		                var codePoint = swapEndian((words[i >>> 2] >>> (16 - (i % 4) * 8)) & 0xffff);
		                utf16Chars.push(String.fromCharCode(codePoint));
		            }
	
		            return utf16Chars.join('');
		        },
	
		        /**
		         * Converts a UTF-16 LE string to a word array.
		         *
		         * @param {string} utf16Str The UTF-16 LE string.
		         *
		         * @return {WordArray} The word array.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var wordArray = CryptoJS.enc.Utf16LE.parse(utf16Str);
		         */
		        parse: function (utf16Str) {
		            // Shortcut
		            var utf16StrLength = utf16Str.length;
	
		            // Convert
		            var words = [];
		            for (var i = 0; i < utf16StrLength; i++) {
		                words[i >>> 1] |= swapEndian(utf16Str.charCodeAt(i) << (16 - (i % 2) * 16));
		            }
	
		            return WordArray.create(words, utf16StrLength * 2);
		        }
		    };
	
		    function swapEndian(word) {
		        return ((word << 8) & 0xff00ff00) | ((word >>> 8) & 0x00ff00ff);
		    }
		}());
	
	
		return CryptoJS.enc.Utf16;
	
	}));

/***/ }),
/* 106 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		(function () {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var WordArray = C_lib.WordArray;
		    var C_enc = C.enc;
	
		    /**
		     * Base64 encoding strategy.
		     */
		    var Base64 = C_enc.Base64 = {
		        /**
		         * Converts a word array to a Base64 string.
		         *
		         * @param {WordArray} wordArray The word array.
		         *
		         * @return {string} The Base64 string.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var base64String = CryptoJS.enc.Base64.stringify(wordArray);
		         */
		        stringify: function (wordArray) {
		            // Shortcuts
		            var words = wordArray.words;
		            var sigBytes = wordArray.sigBytes;
		            var map = this._map;
	
		            // Clamp excess bits
		            wordArray.clamp();
	
		            // Convert
		            var base64Chars = [];
		            for (var i = 0; i < sigBytes; i += 3) {
		                var byte1 = (words[i >>> 2]       >>> (24 - (i % 4) * 8))       & 0xff;
		                var byte2 = (words[(i + 1) >>> 2] >>> (24 - ((i + 1) % 4) * 8)) & 0xff;
		                var byte3 = (words[(i + 2) >>> 2] >>> (24 - ((i + 2) % 4) * 8)) & 0xff;
	
		                var triplet = (byte1 << 16) | (byte2 << 8) | byte3;
	
		                for (var j = 0; (j < 4) && (i + j * 0.75 < sigBytes); j++) {
		                    base64Chars.push(map.charAt((triplet >>> (6 * (3 - j))) & 0x3f));
		                }
		            }
	
		            // Add padding
		            var paddingChar = map.charAt(64);
		            if (paddingChar) {
		                while (base64Chars.length % 4) {
		                    base64Chars.push(paddingChar);
		                }
		            }
	
		            return base64Chars.join('');
		        },
	
		        /**
		         * Converts a Base64 string to a word array.
		         *
		         * @param {string} base64Str The Base64 string.
		         *
		         * @return {WordArray} The word array.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var wordArray = CryptoJS.enc.Base64.parse(base64String);
		         */
		        parse: function (base64Str) {
		            // Shortcuts
		            var base64StrLength = base64Str.length;
		            var map = this._map;
		            var reverseMap = this._reverseMap;
	
		            if (!reverseMap) {
		                    reverseMap = this._reverseMap = [];
		                    for (var j = 0; j < map.length; j++) {
		                        reverseMap[map.charCodeAt(j)] = j;
		                    }
		            }
	
		            // Ignore padding
		            var paddingChar = map.charAt(64);
		            if (paddingChar) {
		                var paddingIndex = base64Str.indexOf(paddingChar);
		                if (paddingIndex !== -1) {
		                    base64StrLength = paddingIndex;
		                }
		            }
	
		            // Convert
		            return parseLoop(base64Str, base64StrLength, reverseMap);
	
		        },
	
		        _map: 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/='
		    };
	
		    function parseLoop(base64Str, base64StrLength, reverseMap) {
		      var words = [];
		      var nBytes = 0;
		      for (var i = 0; i < base64StrLength; i++) {
		          if (i % 4) {
		              var bits1 = reverseMap[base64Str.charCodeAt(i - 1)] << ((i % 4) * 2);
		              var bits2 = reverseMap[base64Str.charCodeAt(i)] >>> (6 - (i % 4) * 2);
		              words[nBytes >>> 2] |= (bits1 | bits2) << (24 - (nBytes % 4) * 8);
		              nBytes++;
		          }
		      }
		      return WordArray.create(words, nBytes);
		    }
		}());
	
	
		return CryptoJS.enc.Base64;
	
	}));

/***/ }),
/* 107 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		(function (Math) {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var WordArray = C_lib.WordArray;
		    var Hasher = C_lib.Hasher;
		    var C_algo = C.algo;
	
		    // Constants table
		    var T = [];
	
		    // Compute constants
		    (function () {
		        for (var i = 0; i < 64; i++) {
		            T[i] = (Math.abs(Math.sin(i + 1)) * 0x100000000) | 0;
		        }
		    }());
	
		    /**
		     * MD5 hash algorithm.
		     */
		    var MD5 = C_algo.MD5 = Hasher.extend({
		        _doReset: function () {
		            this._hash = new WordArray.init([
		                0x67452301, 0xefcdab89,
		                0x98badcfe, 0x10325476
		            ]);
		        },
	
		        _doProcessBlock: function (M, offset) {
		            // Swap endian
		            for (var i = 0; i < 16; i++) {
		                // Shortcuts
		                var offset_i = offset + i;
		                var M_offset_i = M[offset_i];
	
		                M[offset_i] = (
		                    (((M_offset_i << 8)  | (M_offset_i >>> 24)) & 0x00ff00ff) |
		                    (((M_offset_i << 24) | (M_offset_i >>> 8))  & 0xff00ff00)
		                );
		            }
	
		            // Shortcuts
		            var H = this._hash.words;
	
		            var M_offset_0  = M[offset + 0];
		            var M_offset_1  = M[offset + 1];
		            var M_offset_2  = M[offset + 2];
		            var M_offset_3  = M[offset + 3];
		            var M_offset_4  = M[offset + 4];
		            var M_offset_5  = M[offset + 5];
		            var M_offset_6  = M[offset + 6];
		            var M_offset_7  = M[offset + 7];
		            var M_offset_8  = M[offset + 8];
		            var M_offset_9  = M[offset + 9];
		            var M_offset_10 = M[offset + 10];
		            var M_offset_11 = M[offset + 11];
		            var M_offset_12 = M[offset + 12];
		            var M_offset_13 = M[offset + 13];
		            var M_offset_14 = M[offset + 14];
		            var M_offset_15 = M[offset + 15];
	
		            // Working varialbes
		            var a = H[0];
		            var b = H[1];
		            var c = H[2];
		            var d = H[3];
	
		            // Computation
		            a = FF(a, b, c, d, M_offset_0,  7,  T[0]);
		            d = FF(d, a, b, c, M_offset_1,  12, T[1]);
		            c = FF(c, d, a, b, M_offset_2,  17, T[2]);
		            b = FF(b, c, d, a, M_offset_3,  22, T[3]);
		            a = FF(a, b, c, d, M_offset_4,  7,  T[4]);
		            d = FF(d, a, b, c, M_offset_5,  12, T[5]);
		            c = FF(c, d, a, b, M_offset_6,  17, T[6]);
		            b = FF(b, c, d, a, M_offset_7,  22, T[7]);
		            a = FF(a, b, c, d, M_offset_8,  7,  T[8]);
		            d = FF(d, a, b, c, M_offset_9,  12, T[9]);
		            c = FF(c, d, a, b, M_offset_10, 17, T[10]);
		            b = FF(b, c, d, a, M_offset_11, 22, T[11]);
		            a = FF(a, b, c, d, M_offset_12, 7,  T[12]);
		            d = FF(d, a, b, c, M_offset_13, 12, T[13]);
		            c = FF(c, d, a, b, M_offset_14, 17, T[14]);
		            b = FF(b, c, d, a, M_offset_15, 22, T[15]);
	
		            a = GG(a, b, c, d, M_offset_1,  5,  T[16]);
		            d = GG(d, a, b, c, M_offset_6,  9,  T[17]);
		            c = GG(c, d, a, b, M_offset_11, 14, T[18]);
		            b = GG(b, c, d, a, M_offset_0,  20, T[19]);
		            a = GG(a, b, c, d, M_offset_5,  5,  T[20]);
		            d = GG(d, a, b, c, M_offset_10, 9,  T[21]);
		            c = GG(c, d, a, b, M_offset_15, 14, T[22]);
		            b = GG(b, c, d, a, M_offset_4,  20, T[23]);
		            a = GG(a, b, c, d, M_offset_9,  5,  T[24]);
		            d = GG(d, a, b, c, M_offset_14, 9,  T[25]);
		            c = GG(c, d, a, b, M_offset_3,  14, T[26]);
		            b = GG(b, c, d, a, M_offset_8,  20, T[27]);
		            a = GG(a, b, c, d, M_offset_13, 5,  T[28]);
		            d = GG(d, a, b, c, M_offset_2,  9,  T[29]);
		            c = GG(c, d, a, b, M_offset_7,  14, T[30]);
		            b = GG(b, c, d, a, M_offset_12, 20, T[31]);
	
		            a = HH(a, b, c, d, M_offset_5,  4,  T[32]);
		            d = HH(d, a, b, c, M_offset_8,  11, T[33]);
		            c = HH(c, d, a, b, M_offset_11, 16, T[34]);
		            b = HH(b, c, d, a, M_offset_14, 23, T[35]);
		            a = HH(a, b, c, d, M_offset_1,  4,  T[36]);
		            d = HH(d, a, b, c, M_offset_4,  11, T[37]);
		            c = HH(c, d, a, b, M_offset_7,  16, T[38]);
		            b = HH(b, c, d, a, M_offset_10, 23, T[39]);
		            a = HH(a, b, c, d, M_offset_13, 4,  T[40]);
		            d = HH(d, a, b, c, M_offset_0,  11, T[41]);
		            c = HH(c, d, a, b, M_offset_3,  16, T[42]);
		            b = HH(b, c, d, a, M_offset_6,  23, T[43]);
		            a = HH(a, b, c, d, M_offset_9,  4,  T[44]);
		            d = HH(d, a, b, c, M_offset_12, 11, T[45]);
		            c = HH(c, d, a, b, M_offset_15, 16, T[46]);
		            b = HH(b, c, d, a, M_offset_2,  23, T[47]);
	
		            a = II(a, b, c, d, M_offset_0,  6,  T[48]);
		            d = II(d, a, b, c, M_offset_7,  10, T[49]);
		            c = II(c, d, a, b, M_offset_14, 15, T[50]);
		            b = II(b, c, d, a, M_offset_5,  21, T[51]);
		            a = II(a, b, c, d, M_offset_12, 6,  T[52]);
		            d = II(d, a, b, c, M_offset_3,  10, T[53]);
		            c = II(c, d, a, b, M_offset_10, 15, T[54]);
		            b = II(b, c, d, a, M_offset_1,  21, T[55]);
		            a = II(a, b, c, d, M_offset_8,  6,  T[56]);
		            d = II(d, a, b, c, M_offset_15, 10, T[57]);
		            c = II(c, d, a, b, M_offset_6,  15, T[58]);
		            b = II(b, c, d, a, M_offset_13, 21, T[59]);
		            a = II(a, b, c, d, M_offset_4,  6,  T[60]);
		            d = II(d, a, b, c, M_offset_11, 10, T[61]);
		            c = II(c, d, a, b, M_offset_2,  15, T[62]);
		            b = II(b, c, d, a, M_offset_9,  21, T[63]);
	
		            // Intermediate hash value
		            H[0] = (H[0] + a) | 0;
		            H[1] = (H[1] + b) | 0;
		            H[2] = (H[2] + c) | 0;
		            H[3] = (H[3] + d) | 0;
		        },
	
		        _doFinalize: function () {
		            // Shortcuts
		            var data = this._data;
		            var dataWords = data.words;
	
		            var nBitsTotal = this._nDataBytes * 8;
		            var nBitsLeft = data.sigBytes * 8;
	
		            // Add padding
		            dataWords[nBitsLeft >>> 5] |= 0x80 << (24 - nBitsLeft % 32);
	
		            var nBitsTotalH = Math.floor(nBitsTotal / 0x100000000);
		            var nBitsTotalL = nBitsTotal;
		            dataWords[(((nBitsLeft + 64) >>> 9) << 4) + 15] = (
		                (((nBitsTotalH << 8)  | (nBitsTotalH >>> 24)) & 0x00ff00ff) |
		                (((nBitsTotalH << 24) | (nBitsTotalH >>> 8))  & 0xff00ff00)
		            );
		            dataWords[(((nBitsLeft + 64) >>> 9) << 4) + 14] = (
		                (((nBitsTotalL << 8)  | (nBitsTotalL >>> 24)) & 0x00ff00ff) |
		                (((nBitsTotalL << 24) | (nBitsTotalL >>> 8))  & 0xff00ff00)
		            );
	
		            data.sigBytes = (dataWords.length + 1) * 4;
	
		            // Hash final blocks
		            this._process();
	
		            // Shortcuts
		            var hash = this._hash;
		            var H = hash.words;
	
		            // Swap endian
		            for (var i = 0; i < 4; i++) {
		                // Shortcut
		                var H_i = H[i];
	
		                H[i] = (((H_i << 8)  | (H_i >>> 24)) & 0x00ff00ff) |
		                       (((H_i << 24) | (H_i >>> 8))  & 0xff00ff00);
		            }
	
		            // Return final computed hash
		            return hash;
		        },
	
		        clone: function () {
		            var clone = Hasher.clone.call(this);
		            clone._hash = this._hash.clone();
	
		            return clone;
		        }
		    });
	
		    function FF(a, b, c, d, x, s, t) {
		        var n = a + ((b & c) | (~b & d)) + x + t;
		        return ((n << s) | (n >>> (32 - s))) + b;
		    }
	
		    function GG(a, b, c, d, x, s, t) {
		        var n = a + ((b & d) | (c & ~d)) + x + t;
		        return ((n << s) | (n >>> (32 - s))) + b;
		    }
	
		    function HH(a, b, c, d, x, s, t) {
		        var n = a + (b ^ c ^ d) + x + t;
		        return ((n << s) | (n >>> (32 - s))) + b;
		    }
	
		    function II(a, b, c, d, x, s, t) {
		        var n = a + (c ^ (b | ~d)) + x + t;
		        return ((n << s) | (n >>> (32 - s))) + b;
		    }
	
		    /**
		     * Shortcut function to the hasher's object interface.
		     *
		     * @param {WordArray|string} message The message to hash.
		     *
		     * @return {WordArray} The hash.
		     *
		     * @static
		     *
		     * @example
		     *
		     *     var hash = CryptoJS.MD5('message');
		     *     var hash = CryptoJS.MD5(wordArray);
		     */
		    C.MD5 = Hasher._createHelper(MD5);
	
		    /**
		     * Shortcut function to the HMAC's object interface.
		     *
		     * @param {WordArray|string} message The message to hash.
		     * @param {WordArray|string} key The secret key.
		     *
		     * @return {WordArray} The HMAC.
		     *
		     * @static
		     *
		     * @example
		     *
		     *     var hmac = CryptoJS.HmacMD5(message, key);
		     */
		    C.HmacMD5 = Hasher._createHmacHelper(MD5);
		}(Math));
	
	
		return CryptoJS.MD5;
	
	}));

/***/ }),
/* 108 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		(function () {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var WordArray = C_lib.WordArray;
		    var Hasher = C_lib.Hasher;
		    var C_algo = C.algo;
	
		    // Reusable object
		    var W = [];
	
		    /**
		     * SHA-1 hash algorithm.
		     */
		    var SHA1 = C_algo.SHA1 = Hasher.extend({
		        _doReset: function () {
		            this._hash = new WordArray.init([
		                0x67452301, 0xefcdab89,
		                0x98badcfe, 0x10325476,
		                0xc3d2e1f0
		            ]);
		        },
	
		        _doProcessBlock: function (M, offset) {
		            // Shortcut
		            var H = this._hash.words;
	
		            // Working variables
		            var a = H[0];
		            var b = H[1];
		            var c = H[2];
		            var d = H[3];
		            var e = H[4];
	
		            // Computation
		            for (var i = 0; i < 80; i++) {
		                if (i < 16) {
		                    W[i] = M[offset + i] | 0;
		                } else {
		                    var n = W[i - 3] ^ W[i - 8] ^ W[i - 14] ^ W[i - 16];
		                    W[i] = (n << 1) | (n >>> 31);
		                }
	
		                var t = ((a << 5) | (a >>> 27)) + e + W[i];
		                if (i < 20) {
		                    t += ((b & c) | (~b & d)) + 0x5a827999;
		                } else if (i < 40) {
		                    t += (b ^ c ^ d) + 0x6ed9eba1;
		                } else if (i < 60) {
		                    t += ((b & c) | (b & d) | (c & d)) - 0x70e44324;
		                } else /* if (i < 80) */ {
		                    t += (b ^ c ^ d) - 0x359d3e2a;
		                }
	
		                e = d;
		                d = c;
		                c = (b << 30) | (b >>> 2);
		                b = a;
		                a = t;
		            }
	
		            // Intermediate hash value
		            H[0] = (H[0] + a) | 0;
		            H[1] = (H[1] + b) | 0;
		            H[2] = (H[2] + c) | 0;
		            H[3] = (H[3] + d) | 0;
		            H[4] = (H[4] + e) | 0;
		        },
	
		        _doFinalize: function () {
		            // Shortcuts
		            var data = this._data;
		            var dataWords = data.words;
	
		            var nBitsTotal = this._nDataBytes * 8;
		            var nBitsLeft = data.sigBytes * 8;
	
		            // Add padding
		            dataWords[nBitsLeft >>> 5] |= 0x80 << (24 - nBitsLeft % 32);
		            dataWords[(((nBitsLeft + 64) >>> 9) << 4) + 14] = Math.floor(nBitsTotal / 0x100000000);
		            dataWords[(((nBitsLeft + 64) >>> 9) << 4) + 15] = nBitsTotal;
		            data.sigBytes = dataWords.length * 4;
	
		            // Hash final blocks
		            this._process();
	
		            // Return final computed hash
		            return this._hash;
		        },
	
		        clone: function () {
		            var clone = Hasher.clone.call(this);
		            clone._hash = this._hash.clone();
	
		            return clone;
		        }
		    });
	
		    /**
		     * Shortcut function to the hasher's object interface.
		     *
		     * @param {WordArray|string} message The message to hash.
		     *
		     * @return {WordArray} The hash.
		     *
		     * @static
		     *
		     * @example
		     *
		     *     var hash = CryptoJS.SHA1('message');
		     *     var hash = CryptoJS.SHA1(wordArray);
		     */
		    C.SHA1 = Hasher._createHelper(SHA1);
	
		    /**
		     * Shortcut function to the HMAC's object interface.
		     *
		     * @param {WordArray|string} message The message to hash.
		     * @param {WordArray|string} key The secret key.
		     *
		     * @return {WordArray} The HMAC.
		     *
		     * @static
		     *
		     * @example
		     *
		     *     var hmac = CryptoJS.HmacSHA1(message, key);
		     */
		    C.HmacSHA1 = Hasher._createHmacHelper(SHA1);
		}());
	
	
		return CryptoJS.SHA1;
	
	}));

/***/ }),
/* 109 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		(function (Math) {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var WordArray = C_lib.WordArray;
		    var Hasher = C_lib.Hasher;
		    var C_algo = C.algo;
	
		    // Initialization and round constants tables
		    var H = [];
		    var K = [];
	
		    // Compute constants
		    (function () {
		        function isPrime(n) {
		            var sqrtN = Math.sqrt(n);
		            for (var factor = 2; factor <= sqrtN; factor++) {
		                if (!(n % factor)) {
		                    return false;
		                }
		            }
	
		            return true;
		        }
	
		        function getFractionalBits(n) {
		            return ((n - (n | 0)) * 0x100000000) | 0;
		        }
	
		        var n = 2;
		        var nPrime = 0;
		        while (nPrime < 64) {
		            if (isPrime(n)) {
		                if (nPrime < 8) {
		                    H[nPrime] = getFractionalBits(Math.pow(n, 1 / 2));
		                }
		                K[nPrime] = getFractionalBits(Math.pow(n, 1 / 3));
	
		                nPrime++;
		            }
	
		            n++;
		        }
		    }());
	
		    // Reusable object
		    var W = [];
	
		    /**
		     * SHA-256 hash algorithm.
		     */
		    var SHA256 = C_algo.SHA256 = Hasher.extend({
		        _doReset: function () {
		            this._hash = new WordArray.init(H.slice(0));
		        },
	
		        _doProcessBlock: function (M, offset) {
		            // Shortcut
		            var H = this._hash.words;
	
		            // Working variables
		            var a = H[0];
		            var b = H[1];
		            var c = H[2];
		            var d = H[3];
		            var e = H[4];
		            var f = H[5];
		            var g = H[6];
		            var h = H[7];
	
		            // Computation
		            for (var i = 0; i < 64; i++) {
		                if (i < 16) {
		                    W[i] = M[offset + i] | 0;
		                } else {
		                    var gamma0x = W[i - 15];
		                    var gamma0  = ((gamma0x << 25) | (gamma0x >>> 7))  ^
		                                  ((gamma0x << 14) | (gamma0x >>> 18)) ^
		                                   (gamma0x >>> 3);
	
		                    var gamma1x = W[i - 2];
		                    var gamma1  = ((gamma1x << 15) | (gamma1x >>> 17)) ^
		                                  ((gamma1x << 13) | (gamma1x >>> 19)) ^
		                                   (gamma1x >>> 10);
	
		                    W[i] = gamma0 + W[i - 7] + gamma1 + W[i - 16];
		                }
	
		                var ch  = (e & f) ^ (~e & g);
		                var maj = (a & b) ^ (a & c) ^ (b & c);
	
		                var sigma0 = ((a << 30) | (a >>> 2)) ^ ((a << 19) | (a >>> 13)) ^ ((a << 10) | (a >>> 22));
		                var sigma1 = ((e << 26) | (e >>> 6)) ^ ((e << 21) | (e >>> 11)) ^ ((e << 7)  | (e >>> 25));
	
		                var t1 = h + sigma1 + ch + K[i] + W[i];
		                var t2 = sigma0 + maj;
	
		                h = g;
		                g = f;
		                f = e;
		                e = (d + t1) | 0;
		                d = c;
		                c = b;
		                b = a;
		                a = (t1 + t2) | 0;
		            }
	
		            // Intermediate hash value
		            H[0] = (H[0] + a) | 0;
		            H[1] = (H[1] + b) | 0;
		            H[2] = (H[2] + c) | 0;
		            H[3] = (H[3] + d) | 0;
		            H[4] = (H[4] + e) | 0;
		            H[5] = (H[5] + f) | 0;
		            H[6] = (H[6] + g) | 0;
		            H[7] = (H[7] + h) | 0;
		        },
	
		        _doFinalize: function () {
		            // Shortcuts
		            var data = this._data;
		            var dataWords = data.words;
	
		            var nBitsTotal = this._nDataBytes * 8;
		            var nBitsLeft = data.sigBytes * 8;
	
		            // Add padding
		            dataWords[nBitsLeft >>> 5] |= 0x80 << (24 - nBitsLeft % 32);
		            dataWords[(((nBitsLeft + 64) >>> 9) << 4) + 14] = Math.floor(nBitsTotal / 0x100000000);
		            dataWords[(((nBitsLeft + 64) >>> 9) << 4) + 15] = nBitsTotal;
		            data.sigBytes = dataWords.length * 4;
	
		            // Hash final blocks
		            this._process();
	
		            // Return final computed hash
		            return this._hash;
		        },
	
		        clone: function () {
		            var clone = Hasher.clone.call(this);
		            clone._hash = this._hash.clone();
	
		            return clone;
		        }
		    });
	
		    /**
		     * Shortcut function to the hasher's object interface.
		     *
		     * @param {WordArray|string} message The message to hash.
		     *
		     * @return {WordArray} The hash.
		     *
		     * @static
		     *
		     * @example
		     *
		     *     var hash = CryptoJS.SHA256('message');
		     *     var hash = CryptoJS.SHA256(wordArray);
		     */
		    C.SHA256 = Hasher._createHelper(SHA256);
	
		    /**
		     * Shortcut function to the HMAC's object interface.
		     *
		     * @param {WordArray|string} message The message to hash.
		     * @param {WordArray|string} key The secret key.
		     *
		     * @return {WordArray} The HMAC.
		     *
		     * @static
		     *
		     * @example
		     *
		     *     var hmac = CryptoJS.HmacSHA256(message, key);
		     */
		    C.HmacSHA256 = Hasher._createHmacHelper(SHA256);
		}(Math));
	
	
		return CryptoJS.SHA256;
	
	}));

/***/ }),
/* 110 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(109));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./sha256"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		(function () {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var WordArray = C_lib.WordArray;
		    var C_algo = C.algo;
		    var SHA256 = C_algo.SHA256;
	
		    /**
		     * SHA-224 hash algorithm.
		     */
		    var SHA224 = C_algo.SHA224 = SHA256.extend({
		        _doReset: function () {
		            this._hash = new WordArray.init([
		                0xc1059ed8, 0x367cd507, 0x3070dd17, 0xf70e5939,
		                0xffc00b31, 0x68581511, 0x64f98fa7, 0xbefa4fa4
		            ]);
		        },
	
		        _doFinalize: function () {
		            var hash = SHA256._doFinalize.call(this);
	
		            hash.sigBytes -= 4;
	
		            return hash;
		        }
		    });
	
		    /**
		     * Shortcut function to the hasher's object interface.
		     *
		     * @param {WordArray|string} message The message to hash.
		     *
		     * @return {WordArray} The hash.
		     *
		     * @static
		     *
		     * @example
		     *
		     *     var hash = CryptoJS.SHA224('message');
		     *     var hash = CryptoJS.SHA224(wordArray);
		     */
		    C.SHA224 = SHA256._createHelper(SHA224);
	
		    /**
		     * Shortcut function to the HMAC's object interface.
		     *
		     * @param {WordArray|string} message The message to hash.
		     * @param {WordArray|string} key The secret key.
		     *
		     * @return {WordArray} The HMAC.
		     *
		     * @static
		     *
		     * @example
		     *
		     *     var hmac = CryptoJS.HmacSHA224(message, key);
		     */
		    C.HmacSHA224 = SHA256._createHmacHelper(SHA224);
		}());
	
	
		return CryptoJS.SHA224;
	
	}));

/***/ }),
/* 111 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(103));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./x64-core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		(function () {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var Hasher = C_lib.Hasher;
		    var C_x64 = C.x64;
		    var X64Word = C_x64.Word;
		    var X64WordArray = C_x64.WordArray;
		    var C_algo = C.algo;
	
		    function X64Word_create() {
		        return X64Word.create.apply(X64Word, arguments);
		    }
	
		    // Constants
		    var K = [
		        X64Word_create(0x428a2f98, 0xd728ae22), X64Word_create(0x71374491, 0x23ef65cd),
		        X64Word_create(0xb5c0fbcf, 0xec4d3b2f), X64Word_create(0xe9b5dba5, 0x8189dbbc),
		        X64Word_create(0x3956c25b, 0xf348b538), X64Word_create(0x59f111f1, 0xb605d019),
		        X64Word_create(0x923f82a4, 0xaf194f9b), X64Word_create(0xab1c5ed5, 0xda6d8118),
		        X64Word_create(0xd807aa98, 0xa3030242), X64Word_create(0x12835b01, 0x45706fbe),
		        X64Word_create(0x243185be, 0x4ee4b28c), X64Word_create(0x550c7dc3, 0xd5ffb4e2),
		        X64Word_create(0x72be5d74, 0xf27b896f), X64Word_create(0x80deb1fe, 0x3b1696b1),
		        X64Word_create(0x9bdc06a7, 0x25c71235), X64Word_create(0xc19bf174, 0xcf692694),
		        X64Word_create(0xe49b69c1, 0x9ef14ad2), X64Word_create(0xefbe4786, 0x384f25e3),
		        X64Word_create(0x0fc19dc6, 0x8b8cd5b5), X64Word_create(0x240ca1cc, 0x77ac9c65),
		        X64Word_create(0x2de92c6f, 0x592b0275), X64Word_create(0x4a7484aa, 0x6ea6e483),
		        X64Word_create(0x5cb0a9dc, 0xbd41fbd4), X64Word_create(0x76f988da, 0x831153b5),
		        X64Word_create(0x983e5152, 0xee66dfab), X64Word_create(0xa831c66d, 0x2db43210),
		        X64Word_create(0xb00327c8, 0x98fb213f), X64Word_create(0xbf597fc7, 0xbeef0ee4),
		        X64Word_create(0xc6e00bf3, 0x3da88fc2), X64Word_create(0xd5a79147, 0x930aa725),
		        X64Word_create(0x06ca6351, 0xe003826f), X64Word_create(0x14292967, 0x0a0e6e70),
		        X64Word_create(0x27b70a85, 0x46d22ffc), X64Word_create(0x2e1b2138, 0x5c26c926),
		        X64Word_create(0x4d2c6dfc, 0x5ac42aed), X64Word_create(0x53380d13, 0x9d95b3df),
		        X64Word_create(0x650a7354, 0x8baf63de), X64Word_create(0x766a0abb, 0x3c77b2a8),
		        X64Word_create(0x81c2c92e, 0x47edaee6), X64Word_create(0x92722c85, 0x1482353b),
		        X64Word_create(0xa2bfe8a1, 0x4cf10364), X64Word_create(0xa81a664b, 0xbc423001),
		        X64Word_create(0xc24b8b70, 0xd0f89791), X64Word_create(0xc76c51a3, 0x0654be30),
		        X64Word_create(0xd192e819, 0xd6ef5218), X64Word_create(0xd6990624, 0x5565a910),
		        X64Word_create(0xf40e3585, 0x5771202a), X64Word_create(0x106aa070, 0x32bbd1b8),
		        X64Word_create(0x19a4c116, 0xb8d2d0c8), X64Word_create(0x1e376c08, 0x5141ab53),
		        X64Word_create(0x2748774c, 0xdf8eeb99), X64Word_create(0x34b0bcb5, 0xe19b48a8),
		        X64Word_create(0x391c0cb3, 0xc5c95a63), X64Word_create(0x4ed8aa4a, 0xe3418acb),
		        X64Word_create(0x5b9cca4f, 0x7763e373), X64Word_create(0x682e6ff3, 0xd6b2b8a3),
		        X64Word_create(0x748f82ee, 0x5defb2fc), X64Word_create(0x78a5636f, 0x43172f60),
		        X64Word_create(0x84c87814, 0xa1f0ab72), X64Word_create(0x8cc70208, 0x1a6439ec),
		        X64Word_create(0x90befffa, 0x23631e28), X64Word_create(0xa4506ceb, 0xde82bde9),
		        X64Word_create(0xbef9a3f7, 0xb2c67915), X64Word_create(0xc67178f2, 0xe372532b),
		        X64Word_create(0xca273ece, 0xea26619c), X64Word_create(0xd186b8c7, 0x21c0c207),
		        X64Word_create(0xeada7dd6, 0xcde0eb1e), X64Word_create(0xf57d4f7f, 0xee6ed178),
		        X64Word_create(0x06f067aa, 0x72176fba), X64Word_create(0x0a637dc5, 0xa2c898a6),
		        X64Word_create(0x113f9804, 0xbef90dae), X64Word_create(0x1b710b35, 0x131c471b),
		        X64Word_create(0x28db77f5, 0x23047d84), X64Word_create(0x32caab7b, 0x40c72493),
		        X64Word_create(0x3c9ebe0a, 0x15c9bebc), X64Word_create(0x431d67c4, 0x9c100d4c),
		        X64Word_create(0x4cc5d4be, 0xcb3e42b6), X64Word_create(0x597f299c, 0xfc657e2a),
		        X64Word_create(0x5fcb6fab, 0x3ad6faec), X64Word_create(0x6c44198c, 0x4a475817)
		    ];
	
		    // Reusable objects
		    var W = [];
		    (function () {
		        for (var i = 0; i < 80; i++) {
		            W[i] = X64Word_create();
		        }
		    }());
	
		    /**
		     * SHA-512 hash algorithm.
		     */
		    var SHA512 = C_algo.SHA512 = Hasher.extend({
		        _doReset: function () {
		            this._hash = new X64WordArray.init([
		                new X64Word.init(0x6a09e667, 0xf3bcc908), new X64Word.init(0xbb67ae85, 0x84caa73b),
		                new X64Word.init(0x3c6ef372, 0xfe94f82b), new X64Word.init(0xa54ff53a, 0x5f1d36f1),
		                new X64Word.init(0x510e527f, 0xade682d1), new X64Word.init(0x9b05688c, 0x2b3e6c1f),
		                new X64Word.init(0x1f83d9ab, 0xfb41bd6b), new X64Word.init(0x5be0cd19, 0x137e2179)
		            ]);
		        },
	
		        _doProcessBlock: function (M, offset) {
		            // Shortcuts
		            var H = this._hash.words;
	
		            var H0 = H[0];
		            var H1 = H[1];
		            var H2 = H[2];
		            var H3 = H[3];
		            var H4 = H[4];
		            var H5 = H[5];
		            var H6 = H[6];
		            var H7 = H[7];
	
		            var H0h = H0.high;
		            var H0l = H0.low;
		            var H1h = H1.high;
		            var H1l = H1.low;
		            var H2h = H2.high;
		            var H2l = H2.low;
		            var H3h = H3.high;
		            var H3l = H3.low;
		            var H4h = H4.high;
		            var H4l = H4.low;
		            var H5h = H5.high;
		            var H5l = H5.low;
		            var H6h = H6.high;
		            var H6l = H6.low;
		            var H7h = H7.high;
		            var H7l = H7.low;
	
		            // Working variables
		            var ah = H0h;
		            var al = H0l;
		            var bh = H1h;
		            var bl = H1l;
		            var ch = H2h;
		            var cl = H2l;
		            var dh = H3h;
		            var dl = H3l;
		            var eh = H4h;
		            var el = H4l;
		            var fh = H5h;
		            var fl = H5l;
		            var gh = H6h;
		            var gl = H6l;
		            var hh = H7h;
		            var hl = H7l;
	
		            // Rounds
		            for (var i = 0; i < 80; i++) {
		                // Shortcut
		                var Wi = W[i];
	
		                // Extend message
		                if (i < 16) {
		                    var Wih = Wi.high = M[offset + i * 2]     | 0;
		                    var Wil = Wi.low  = M[offset + i * 2 + 1] | 0;
		                } else {
		                    // Gamma0
		                    var gamma0x  = W[i - 15];
		                    var gamma0xh = gamma0x.high;
		                    var gamma0xl = gamma0x.low;
		                    var gamma0h  = ((gamma0xh >>> 1) | (gamma0xl << 31)) ^ ((gamma0xh >>> 8) | (gamma0xl << 24)) ^ (gamma0xh >>> 7);
		                    var gamma0l  = ((gamma0xl >>> 1) | (gamma0xh << 31)) ^ ((gamma0xl >>> 8) | (gamma0xh << 24)) ^ ((gamma0xl >>> 7) | (gamma0xh << 25));
	
		                    // Gamma1
		                    var gamma1x  = W[i - 2];
		                    var gamma1xh = gamma1x.high;
		                    var gamma1xl = gamma1x.low;
		                    var gamma1h  = ((gamma1xh >>> 19) | (gamma1xl << 13)) ^ ((gamma1xh << 3) | (gamma1xl >>> 29)) ^ (gamma1xh >>> 6);
		                    var gamma1l  = ((gamma1xl >>> 19) | (gamma1xh << 13)) ^ ((gamma1xl << 3) | (gamma1xh >>> 29)) ^ ((gamma1xl >>> 6) | (gamma1xh << 26));
	
		                    // W[i] = gamma0 + W[i - 7] + gamma1 + W[i - 16]
		                    var Wi7  = W[i - 7];
		                    var Wi7h = Wi7.high;
		                    var Wi7l = Wi7.low;
	
		                    var Wi16  = W[i - 16];
		                    var Wi16h = Wi16.high;
		                    var Wi16l = Wi16.low;
	
		                    var Wil = gamma0l + Wi7l;
		                    var Wih = gamma0h + Wi7h + ((Wil >>> 0) < (gamma0l >>> 0) ? 1 : 0);
		                    var Wil = Wil + gamma1l;
		                    var Wih = Wih + gamma1h + ((Wil >>> 0) < (gamma1l >>> 0) ? 1 : 0);
		                    var Wil = Wil + Wi16l;
		                    var Wih = Wih + Wi16h + ((Wil >>> 0) < (Wi16l >>> 0) ? 1 : 0);
	
		                    Wi.high = Wih;
		                    Wi.low  = Wil;
		                }
	
		                var chh  = (eh & fh) ^ (~eh & gh);
		                var chl  = (el & fl) ^ (~el & gl);
		                var majh = (ah & bh) ^ (ah & ch) ^ (bh & ch);
		                var majl = (al & bl) ^ (al & cl) ^ (bl & cl);
	
		                var sigma0h = ((ah >>> 28) | (al << 4))  ^ ((ah << 30)  | (al >>> 2)) ^ ((ah << 25) | (al >>> 7));
		                var sigma0l = ((al >>> 28) | (ah << 4))  ^ ((al << 30)  | (ah >>> 2)) ^ ((al << 25) | (ah >>> 7));
		                var sigma1h = ((eh >>> 14) | (el << 18)) ^ ((eh >>> 18) | (el << 14)) ^ ((eh << 23) | (el >>> 9));
		                var sigma1l = ((el >>> 14) | (eh << 18)) ^ ((el >>> 18) | (eh << 14)) ^ ((el << 23) | (eh >>> 9));
	
		                // t1 = h + sigma1 + ch + K[i] + W[i]
		                var Ki  = K[i];
		                var Kih = Ki.high;
		                var Kil = Ki.low;
	
		                var t1l = hl + sigma1l;
		                var t1h = hh + sigma1h + ((t1l >>> 0) < (hl >>> 0) ? 1 : 0);
		                var t1l = t1l + chl;
		                var t1h = t1h + chh + ((t1l >>> 0) < (chl >>> 0) ? 1 : 0);
		                var t1l = t1l + Kil;
		                var t1h = t1h + Kih + ((t1l >>> 0) < (Kil >>> 0) ? 1 : 0);
		                var t1l = t1l + Wil;
		                var t1h = t1h + Wih + ((t1l >>> 0) < (Wil >>> 0) ? 1 : 0);
	
		                // t2 = sigma0 + maj
		                var t2l = sigma0l + majl;
		                var t2h = sigma0h + majh + ((t2l >>> 0) < (sigma0l >>> 0) ? 1 : 0);
	
		                // Update working variables
		                hh = gh;
		                hl = gl;
		                gh = fh;
		                gl = fl;
		                fh = eh;
		                fl = el;
		                el = (dl + t1l) | 0;
		                eh = (dh + t1h + ((el >>> 0) < (dl >>> 0) ? 1 : 0)) | 0;
		                dh = ch;
		                dl = cl;
		                ch = bh;
		                cl = bl;
		                bh = ah;
		                bl = al;
		                al = (t1l + t2l) | 0;
		                ah = (t1h + t2h + ((al >>> 0) < (t1l >>> 0) ? 1 : 0)) | 0;
		            }
	
		            // Intermediate hash value
		            H0l = H0.low  = (H0l + al);
		            H0.high = (H0h + ah + ((H0l >>> 0) < (al >>> 0) ? 1 : 0));
		            H1l = H1.low  = (H1l + bl);
		            H1.high = (H1h + bh + ((H1l >>> 0) < (bl >>> 0) ? 1 : 0));
		            H2l = H2.low  = (H2l + cl);
		            H2.high = (H2h + ch + ((H2l >>> 0) < (cl >>> 0) ? 1 : 0));
		            H3l = H3.low  = (H3l + dl);
		            H3.high = (H3h + dh + ((H3l >>> 0) < (dl >>> 0) ? 1 : 0));
		            H4l = H4.low  = (H4l + el);
		            H4.high = (H4h + eh + ((H4l >>> 0) < (el >>> 0) ? 1 : 0));
		            H5l = H5.low  = (H5l + fl);
		            H5.high = (H5h + fh + ((H5l >>> 0) < (fl >>> 0) ? 1 : 0));
		            H6l = H6.low  = (H6l + gl);
		            H6.high = (H6h + gh + ((H6l >>> 0) < (gl >>> 0) ? 1 : 0));
		            H7l = H7.low  = (H7l + hl);
		            H7.high = (H7h + hh + ((H7l >>> 0) < (hl >>> 0) ? 1 : 0));
		        },
	
		        _doFinalize: function () {
		            // Shortcuts
		            var data = this._data;
		            var dataWords = data.words;
	
		            var nBitsTotal = this._nDataBytes * 8;
		            var nBitsLeft = data.sigBytes * 8;
	
		            // Add padding
		            dataWords[nBitsLeft >>> 5] |= 0x80 << (24 - nBitsLeft % 32);
		            dataWords[(((nBitsLeft + 128) >>> 10) << 5) + 30] = Math.floor(nBitsTotal / 0x100000000);
		            dataWords[(((nBitsLeft + 128) >>> 10) << 5) + 31] = nBitsTotal;
		            data.sigBytes = dataWords.length * 4;
	
		            // Hash final blocks
		            this._process();
	
		            // Convert hash to 32-bit word array before returning
		            var hash = this._hash.toX32();
	
		            // Return final computed hash
		            return hash;
		        },
	
		        clone: function () {
		            var clone = Hasher.clone.call(this);
		            clone._hash = this._hash.clone();
	
		            return clone;
		        },
	
		        blockSize: 1024/32
		    });
	
		    /**
		     * Shortcut function to the hasher's object interface.
		     *
		     * @param {WordArray|string} message The message to hash.
		     *
		     * @return {WordArray} The hash.
		     *
		     * @static
		     *
		     * @example
		     *
		     *     var hash = CryptoJS.SHA512('message');
		     *     var hash = CryptoJS.SHA512(wordArray);
		     */
		    C.SHA512 = Hasher._createHelper(SHA512);
	
		    /**
		     * Shortcut function to the HMAC's object interface.
		     *
		     * @param {WordArray|string} message The message to hash.
		     * @param {WordArray|string} key The secret key.
		     *
		     * @return {WordArray} The HMAC.
		     *
		     * @static
		     *
		     * @example
		     *
		     *     var hmac = CryptoJS.HmacSHA512(message, key);
		     */
		    C.HmacSHA512 = Hasher._createHmacHelper(SHA512);
		}());
	
	
		return CryptoJS.SHA512;
	
	}));

/***/ }),
/* 112 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(103), __webpack_require__(111));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./x64-core", "./sha512"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		(function () {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_x64 = C.x64;
		    var X64Word = C_x64.Word;
		    var X64WordArray = C_x64.WordArray;
		    var C_algo = C.algo;
		    var SHA512 = C_algo.SHA512;
	
		    /**
		     * SHA-384 hash algorithm.
		     */
		    var SHA384 = C_algo.SHA384 = SHA512.extend({
		        _doReset: function () {
		            this._hash = new X64WordArray.init([
		                new X64Word.init(0xcbbb9d5d, 0xc1059ed8), new X64Word.init(0x629a292a, 0x367cd507),
		                new X64Word.init(0x9159015a, 0x3070dd17), new X64Word.init(0x152fecd8, 0xf70e5939),
		                new X64Word.init(0x67332667, 0xffc00b31), new X64Word.init(0x8eb44a87, 0x68581511),
		                new X64Word.init(0xdb0c2e0d, 0x64f98fa7), new X64Word.init(0x47b5481d, 0xbefa4fa4)
		            ]);
		        },
	
		        _doFinalize: function () {
		            var hash = SHA512._doFinalize.call(this);
	
		            hash.sigBytes -= 16;
	
		            return hash;
		        }
		    });
	
		    /**
		     * Shortcut function to the hasher's object interface.
		     *
		     * @param {WordArray|string} message The message to hash.
		     *
		     * @return {WordArray} The hash.
		     *
		     * @static
		     *
		     * @example
		     *
		     *     var hash = CryptoJS.SHA384('message');
		     *     var hash = CryptoJS.SHA384(wordArray);
		     */
		    C.SHA384 = SHA512._createHelper(SHA384);
	
		    /**
		     * Shortcut function to the HMAC's object interface.
		     *
		     * @param {WordArray|string} message The message to hash.
		     * @param {WordArray|string} key The secret key.
		     *
		     * @return {WordArray} The HMAC.
		     *
		     * @static
		     *
		     * @example
		     *
		     *     var hmac = CryptoJS.HmacSHA384(message, key);
		     */
		    C.HmacSHA384 = SHA512._createHmacHelper(SHA384);
		}());
	
	
		return CryptoJS.SHA384;
	
	}));

/***/ }),
/* 113 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(103));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./x64-core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		(function (Math) {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var WordArray = C_lib.WordArray;
		    var Hasher = C_lib.Hasher;
		    var C_x64 = C.x64;
		    var X64Word = C_x64.Word;
		    var C_algo = C.algo;
	
		    // Constants tables
		    var RHO_OFFSETS = [];
		    var PI_INDEXES  = [];
		    var ROUND_CONSTANTS = [];
	
		    // Compute Constants
		    (function () {
		        // Compute rho offset constants
		        var x = 1, y = 0;
		        for (var t = 0; t < 24; t++) {
		            RHO_OFFSETS[x + 5 * y] = ((t + 1) * (t + 2) / 2) % 64;
	
		            var newX = y % 5;
		            var newY = (2 * x + 3 * y) % 5;
		            x = newX;
		            y = newY;
		        }
	
		        // Compute pi index constants
		        for (var x = 0; x < 5; x++) {
		            for (var y = 0; y < 5; y++) {
		                PI_INDEXES[x + 5 * y] = y + ((2 * x + 3 * y) % 5) * 5;
		            }
		        }
	
		        // Compute round constants
		        var LFSR = 0x01;
		        for (var i = 0; i < 24; i++) {
		            var roundConstantMsw = 0;
		            var roundConstantLsw = 0;
	
		            for (var j = 0; j < 7; j++) {
		                if (LFSR & 0x01) {
		                    var bitPosition = (1 << j) - 1;
		                    if (bitPosition < 32) {
		                        roundConstantLsw ^= 1 << bitPosition;
		                    } else /* if (bitPosition >= 32) */ {
		                        roundConstantMsw ^= 1 << (bitPosition - 32);
		                    }
		                }
	
		                // Compute next LFSR
		                if (LFSR & 0x80) {
		                    // Primitive polynomial over GF(2): x^8 + x^6 + x^5 + x^4 + 1
		                    LFSR = (LFSR << 1) ^ 0x71;
		                } else {
		                    LFSR <<= 1;
		                }
		            }
	
		            ROUND_CONSTANTS[i] = X64Word.create(roundConstantMsw, roundConstantLsw);
		        }
		    }());
	
		    // Reusable objects for temporary values
		    var T = [];
		    (function () {
		        for (var i = 0; i < 25; i++) {
		            T[i] = X64Word.create();
		        }
		    }());
	
		    /**
		     * SHA-3 hash algorithm.
		     */
		    var SHA3 = C_algo.SHA3 = Hasher.extend({
		        /**
		         * Configuration options.
		         *
		         * @property {number} outputLength
		         *   The desired number of bits in the output hash.
		         *   Only values permitted are: 224, 256, 384, 512.
		         *   Default: 512
		         */
		        cfg: Hasher.cfg.extend({
		            outputLength: 512
		        }),
	
		        _doReset: function () {
		            var state = this._state = []
		            for (var i = 0; i < 25; i++) {
		                state[i] = new X64Word.init();
		            }
	
		            this.blockSize = (1600 - 2 * this.cfg.outputLength) / 32;
		        },
	
		        _doProcessBlock: function (M, offset) {
		            // Shortcuts
		            var state = this._state;
		            var nBlockSizeLanes = this.blockSize / 2;
	
		            // Absorb
		            for (var i = 0; i < nBlockSizeLanes; i++) {
		                // Shortcuts
		                var M2i  = M[offset + 2 * i];
		                var M2i1 = M[offset + 2 * i + 1];
	
		                // Swap endian
		                M2i = (
		                    (((M2i << 8)  | (M2i >>> 24)) & 0x00ff00ff) |
		                    (((M2i << 24) | (M2i >>> 8))  & 0xff00ff00)
		                );
		                M2i1 = (
		                    (((M2i1 << 8)  | (M2i1 >>> 24)) & 0x00ff00ff) |
		                    (((M2i1 << 24) | (M2i1 >>> 8))  & 0xff00ff00)
		                );
	
		                // Absorb message into state
		                var lane = state[i];
		                lane.high ^= M2i1;
		                lane.low  ^= M2i;
		            }
	
		            // Rounds
		            for (var round = 0; round < 24; round++) {
		                // Theta
		                for (var x = 0; x < 5; x++) {
		                    // Mix column lanes
		                    var tMsw = 0, tLsw = 0;
		                    for (var y = 0; y < 5; y++) {
		                        var lane = state[x + 5 * y];
		                        tMsw ^= lane.high;
		                        tLsw ^= lane.low;
		                    }
	
		                    // Temporary values
		                    var Tx = T[x];
		                    Tx.high = tMsw;
		                    Tx.low  = tLsw;
		                }
		                for (var x = 0; x < 5; x++) {
		                    // Shortcuts
		                    var Tx4 = T[(x + 4) % 5];
		                    var Tx1 = T[(x + 1) % 5];
		                    var Tx1Msw = Tx1.high;
		                    var Tx1Lsw = Tx1.low;
	
		                    // Mix surrounding columns
		                    var tMsw = Tx4.high ^ ((Tx1Msw << 1) | (Tx1Lsw >>> 31));
		                    var tLsw = Tx4.low  ^ ((Tx1Lsw << 1) | (Tx1Msw >>> 31));
		                    for (var y = 0; y < 5; y++) {
		                        var lane = state[x + 5 * y];
		                        lane.high ^= tMsw;
		                        lane.low  ^= tLsw;
		                    }
		                }
	
		                // Rho Pi
		                for (var laneIndex = 1; laneIndex < 25; laneIndex++) {
		                    // Shortcuts
		                    var lane = state[laneIndex];
		                    var laneMsw = lane.high;
		                    var laneLsw = lane.low;
		                    var rhoOffset = RHO_OFFSETS[laneIndex];
	
		                    // Rotate lanes
		                    if (rhoOffset < 32) {
		                        var tMsw = (laneMsw << rhoOffset) | (laneLsw >>> (32 - rhoOffset));
		                        var tLsw = (laneLsw << rhoOffset) | (laneMsw >>> (32 - rhoOffset));
		                    } else /* if (rhoOffset >= 32) */ {
		                        var tMsw = (laneLsw << (rhoOffset - 32)) | (laneMsw >>> (64 - rhoOffset));
		                        var tLsw = (laneMsw << (rhoOffset - 32)) | (laneLsw >>> (64 - rhoOffset));
		                    }
	
		                    // Transpose lanes
		                    var TPiLane = T[PI_INDEXES[laneIndex]];
		                    TPiLane.high = tMsw;
		                    TPiLane.low  = tLsw;
		                }
	
		                // Rho pi at x = y = 0
		                var T0 = T[0];
		                var state0 = state[0];
		                T0.high = state0.high;
		                T0.low  = state0.low;
	
		                // Chi
		                for (var x = 0; x < 5; x++) {
		                    for (var y = 0; y < 5; y++) {
		                        // Shortcuts
		                        var laneIndex = x + 5 * y;
		                        var lane = state[laneIndex];
		                        var TLane = T[laneIndex];
		                        var Tx1Lane = T[((x + 1) % 5) + 5 * y];
		                        var Tx2Lane = T[((x + 2) % 5) + 5 * y];
	
		                        // Mix rows
		                        lane.high = TLane.high ^ (~Tx1Lane.high & Tx2Lane.high);
		                        lane.low  = TLane.low  ^ (~Tx1Lane.low  & Tx2Lane.low);
		                    }
		                }
	
		                // Iota
		                var lane = state[0];
		                var roundConstant = ROUND_CONSTANTS[round];
		                lane.high ^= roundConstant.high;
		                lane.low  ^= roundConstant.low;;
		            }
		        },
	
		        _doFinalize: function () {
		            // Shortcuts
		            var data = this._data;
		            var dataWords = data.words;
		            var nBitsTotal = this._nDataBytes * 8;
		            var nBitsLeft = data.sigBytes * 8;
		            var blockSizeBits = this.blockSize * 32;
	
		            // Add padding
		            dataWords[nBitsLeft >>> 5] |= 0x1 << (24 - nBitsLeft % 32);
		            dataWords[((Math.ceil((nBitsLeft + 1) / blockSizeBits) * blockSizeBits) >>> 5) - 1] |= 0x80;
		            data.sigBytes = dataWords.length * 4;
	
		            // Hash final blocks
		            this._process();
	
		            // Shortcuts
		            var state = this._state;
		            var outputLengthBytes = this.cfg.outputLength / 8;
		            var outputLengthLanes = outputLengthBytes / 8;
	
		            // Squeeze
		            var hashWords = [];
		            for (var i = 0; i < outputLengthLanes; i++) {
		                // Shortcuts
		                var lane = state[i];
		                var laneMsw = lane.high;
		                var laneLsw = lane.low;
	
		                // Swap endian
		                laneMsw = (
		                    (((laneMsw << 8)  | (laneMsw >>> 24)) & 0x00ff00ff) |
		                    (((laneMsw << 24) | (laneMsw >>> 8))  & 0xff00ff00)
		                );
		                laneLsw = (
		                    (((laneLsw << 8)  | (laneLsw >>> 24)) & 0x00ff00ff) |
		                    (((laneLsw << 24) | (laneLsw >>> 8))  & 0xff00ff00)
		                );
	
		                // Squeeze state to retrieve hash
		                hashWords.push(laneLsw);
		                hashWords.push(laneMsw);
		            }
	
		            // Return final computed hash
		            return new WordArray.init(hashWords, outputLengthBytes);
		        },
	
		        clone: function () {
		            var clone = Hasher.clone.call(this);
	
		            var state = clone._state = this._state.slice(0);
		            for (var i = 0; i < 25; i++) {
		                state[i] = state[i].clone();
		            }
	
		            return clone;
		        }
		    });
	
		    /**
		     * Shortcut function to the hasher's object interface.
		     *
		     * @param {WordArray|string} message The message to hash.
		     *
		     * @return {WordArray} The hash.
		     *
		     * @static
		     *
		     * @example
		     *
		     *     var hash = CryptoJS.SHA3('message');
		     *     var hash = CryptoJS.SHA3(wordArray);
		     */
		    C.SHA3 = Hasher._createHelper(SHA3);
	
		    /**
		     * Shortcut function to the HMAC's object interface.
		     *
		     * @param {WordArray|string} message The message to hash.
		     * @param {WordArray|string} key The secret key.
		     *
		     * @return {WordArray} The HMAC.
		     *
		     * @static
		     *
		     * @example
		     *
		     *     var hmac = CryptoJS.HmacSHA3(message, key);
		     */
		    C.HmacSHA3 = Hasher._createHmacHelper(SHA3);
		}(Math));
	
	
		return CryptoJS.SHA3;
	
	}));

/***/ }),
/* 114 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		/** @preserve
		(c) 2012 by Cédric Mesnil. All rights reserved.
	
		Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
	
		    - Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
		    - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
	
		THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
		*/
	
		(function (Math) {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var WordArray = C_lib.WordArray;
		    var Hasher = C_lib.Hasher;
		    var C_algo = C.algo;
	
		    // Constants table
		    var _zl = WordArray.create([
		        0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14, 15,
		        7,  4, 13,  1, 10,  6, 15,  3, 12,  0,  9,  5,  2, 14, 11,  8,
		        3, 10, 14,  4,  9, 15,  8,  1,  2,  7,  0,  6, 13, 11,  5, 12,
		        1,  9, 11, 10,  0,  8, 12,  4, 13,  3,  7, 15, 14,  5,  6,  2,
		        4,  0,  5,  9,  7, 12,  2, 10, 14,  1,  3,  8, 11,  6, 15, 13]);
		    var _zr = WordArray.create([
		        5, 14,  7,  0,  9,  2, 11,  4, 13,  6, 15,  8,  1, 10,  3, 12,
		        6, 11,  3,  7,  0, 13,  5, 10, 14, 15,  8, 12,  4,  9,  1,  2,
		        15,  5,  1,  3,  7, 14,  6,  9, 11,  8, 12,  2, 10,  0,  4, 13,
		        8,  6,  4,  1,  3, 11, 15,  0,  5, 12,  2, 13,  9,  7, 10, 14,
		        12, 15, 10,  4,  1,  5,  8,  7,  6,  2, 13, 14,  0,  3,  9, 11]);
		    var _sl = WordArray.create([
		         11, 14, 15, 12,  5,  8,  7,  9, 11, 13, 14, 15,  6,  7,  9,  8,
		        7, 6,   8, 13, 11,  9,  7, 15,  7, 12, 15,  9, 11,  7, 13, 12,
		        11, 13,  6,  7, 14,  9, 13, 15, 14,  8, 13,  6,  5, 12,  7,  5,
		          11, 12, 14, 15, 14, 15,  9,  8,  9, 14,  5,  6,  8,  6,  5, 12,
		        9, 15,  5, 11,  6,  8, 13, 12,  5, 12, 13, 14, 11,  8,  5,  6 ]);
		    var _sr = WordArray.create([
		        8,  9,  9, 11, 13, 15, 15,  5,  7,  7,  8, 11, 14, 14, 12,  6,
		        9, 13, 15,  7, 12,  8,  9, 11,  7,  7, 12,  7,  6, 15, 13, 11,
		        9,  7, 15, 11,  8,  6,  6, 14, 12, 13,  5, 14, 13, 13,  7,  5,
		        15,  5,  8, 11, 14, 14,  6, 14,  6,  9, 12,  9, 12,  5, 15,  8,
		        8,  5, 12,  9, 12,  5, 14,  6,  8, 13,  6,  5, 15, 13, 11, 11 ]);
	
		    var _hl =  WordArray.create([ 0x00000000, 0x5A827999, 0x6ED9EBA1, 0x8F1BBCDC, 0xA953FD4E]);
		    var _hr =  WordArray.create([ 0x50A28BE6, 0x5C4DD124, 0x6D703EF3, 0x7A6D76E9, 0x00000000]);
	
		    /**
		     * RIPEMD160 hash algorithm.
		     */
		    var RIPEMD160 = C_algo.RIPEMD160 = Hasher.extend({
		        _doReset: function () {
		            this._hash  = WordArray.create([0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0]);
		        },
	
		        _doProcessBlock: function (M, offset) {
	
		            // Swap endian
		            for (var i = 0; i < 16; i++) {
		                // Shortcuts
		                var offset_i = offset + i;
		                var M_offset_i = M[offset_i];
	
		                // Swap
		                M[offset_i] = (
		                    (((M_offset_i << 8)  | (M_offset_i >>> 24)) & 0x00ff00ff) |
		                    (((M_offset_i << 24) | (M_offset_i >>> 8))  & 0xff00ff00)
		                );
		            }
		            // Shortcut
		            var H  = this._hash.words;
		            var hl = _hl.words;
		            var hr = _hr.words;
		            var zl = _zl.words;
		            var zr = _zr.words;
		            var sl = _sl.words;
		            var sr = _sr.words;
	
		            // Working variables
		            var al, bl, cl, dl, el;
		            var ar, br, cr, dr, er;
	
		            ar = al = H[0];
		            br = bl = H[1];
		            cr = cl = H[2];
		            dr = dl = H[3];
		            er = el = H[4];
		            // Computation
		            var t;
		            for (var i = 0; i < 80; i += 1) {
		                t = (al +  M[offset+zl[i]])|0;
		                if (i<16){
			            t +=  f1(bl,cl,dl) + hl[0];
		                } else if (i<32) {
			            t +=  f2(bl,cl,dl) + hl[1];
		                } else if (i<48) {
			            t +=  f3(bl,cl,dl) + hl[2];
		                } else if (i<64) {
			            t +=  f4(bl,cl,dl) + hl[3];
		                } else {// if (i<80) {
			            t +=  f5(bl,cl,dl) + hl[4];
		                }
		                t = t|0;
		                t =  rotl(t,sl[i]);
		                t = (t+el)|0;
		                al = el;
		                el = dl;
		                dl = rotl(cl, 10);
		                cl = bl;
		                bl = t;
	
		                t = (ar + M[offset+zr[i]])|0;
		                if (i<16){
			            t +=  f5(br,cr,dr) + hr[0];
		                } else if (i<32) {
			            t +=  f4(br,cr,dr) + hr[1];
		                } else if (i<48) {
			            t +=  f3(br,cr,dr) + hr[2];
		                } else if (i<64) {
			            t +=  f2(br,cr,dr) + hr[3];
		                } else {// if (i<80) {
			            t +=  f1(br,cr,dr) + hr[4];
		                }
		                t = t|0;
		                t =  rotl(t,sr[i]) ;
		                t = (t+er)|0;
		                ar = er;
		                er = dr;
		                dr = rotl(cr, 10);
		                cr = br;
		                br = t;
		            }
		            // Intermediate hash value
		            t    = (H[1] + cl + dr)|0;
		            H[1] = (H[2] + dl + er)|0;
		            H[2] = (H[3] + el + ar)|0;
		            H[3] = (H[4] + al + br)|0;
		            H[4] = (H[0] + bl + cr)|0;
		            H[0] =  t;
		        },
	
		        _doFinalize: function () {
		            // Shortcuts
		            var data = this._data;
		            var dataWords = data.words;
	
		            var nBitsTotal = this._nDataBytes * 8;
		            var nBitsLeft = data.sigBytes * 8;
	
		            // Add padding
		            dataWords[nBitsLeft >>> 5] |= 0x80 << (24 - nBitsLeft % 32);
		            dataWords[(((nBitsLeft + 64) >>> 9) << 4) + 14] = (
		                (((nBitsTotal << 8)  | (nBitsTotal >>> 24)) & 0x00ff00ff) |
		                (((nBitsTotal << 24) | (nBitsTotal >>> 8))  & 0xff00ff00)
		            );
		            data.sigBytes = (dataWords.length + 1) * 4;
	
		            // Hash final blocks
		            this._process();
	
		            // Shortcuts
		            var hash = this._hash;
		            var H = hash.words;
	
		            // Swap endian
		            for (var i = 0; i < 5; i++) {
		                // Shortcut
		                var H_i = H[i];
	
		                // Swap
		                H[i] = (((H_i << 8)  | (H_i >>> 24)) & 0x00ff00ff) |
		                       (((H_i << 24) | (H_i >>> 8))  & 0xff00ff00);
		            }
	
		            // Return final computed hash
		            return hash;
		        },
	
		        clone: function () {
		            var clone = Hasher.clone.call(this);
		            clone._hash = this._hash.clone();
	
		            return clone;
		        }
		    });
	
	
		    function f1(x, y, z) {
		        return ((x) ^ (y) ^ (z));
	
		    }
	
		    function f2(x, y, z) {
		        return (((x)&(y)) | ((~x)&(z)));
		    }
	
		    function f3(x, y, z) {
		        return (((x) | (~(y))) ^ (z));
		    }
	
		    function f4(x, y, z) {
		        return (((x) & (z)) | ((y)&(~(z))));
		    }
	
		    function f5(x, y, z) {
		        return ((x) ^ ((y) |(~(z))));
	
		    }
	
		    function rotl(x,n) {
		        return (x<<n) | (x>>>(32-n));
		    }
	
	
		    /**
		     * Shortcut function to the hasher's object interface.
		     *
		     * @param {WordArray|string} message The message to hash.
		     *
		     * @return {WordArray} The hash.
		     *
		     * @static
		     *
		     * @example
		     *
		     *     var hash = CryptoJS.RIPEMD160('message');
		     *     var hash = CryptoJS.RIPEMD160(wordArray);
		     */
		    C.RIPEMD160 = Hasher._createHelper(RIPEMD160);
	
		    /**
		     * Shortcut function to the HMAC's object interface.
		     *
		     * @param {WordArray|string} message The message to hash.
		     * @param {WordArray|string} key The secret key.
		     *
		     * @return {WordArray} The HMAC.
		     *
		     * @static
		     *
		     * @example
		     *
		     *     var hmac = CryptoJS.HmacRIPEMD160(message, key);
		     */
		    C.HmacRIPEMD160 = Hasher._createHmacHelper(RIPEMD160);
		}(Math));
	
	
		return CryptoJS.RIPEMD160;
	
	}));

/***/ }),
/* 115 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		(function () {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var Base = C_lib.Base;
		    var C_enc = C.enc;
		    var Utf8 = C_enc.Utf8;
		    var C_algo = C.algo;
	
		    /**
		     * HMAC algorithm.
		     */
		    var HMAC = C_algo.HMAC = Base.extend({
		        /**
		         * Initializes a newly created HMAC.
		         *
		         * @param {Hasher} hasher The hash algorithm to use.
		         * @param {WordArray|string} key The secret key.
		         *
		         * @example
		         *
		         *     var hmacHasher = CryptoJS.algo.HMAC.create(CryptoJS.algo.SHA256, key);
		         */
		        init: function (hasher, key) {
		            // Init hasher
		            hasher = this._hasher = new hasher.init();
	
		            // Convert string to WordArray, else assume WordArray already
		            if (typeof key == 'string') {
		                key = Utf8.parse(key);
		            }
	
		            // Shortcuts
		            var hasherBlockSize = hasher.blockSize;
		            var hasherBlockSizeBytes = hasherBlockSize * 4;
	
		            // Allow arbitrary length keys
		            if (key.sigBytes > hasherBlockSizeBytes) {
		                key = hasher.finalize(key);
		            }
	
		            // Clamp excess bits
		            key.clamp();
	
		            // Clone key for inner and outer pads
		            var oKey = this._oKey = key.clone();
		            var iKey = this._iKey = key.clone();
	
		            // Shortcuts
		            var oKeyWords = oKey.words;
		            var iKeyWords = iKey.words;
	
		            // XOR keys with pad constants
		            for (var i = 0; i < hasherBlockSize; i++) {
		                oKeyWords[i] ^= 0x5c5c5c5c;
		                iKeyWords[i] ^= 0x36363636;
		            }
		            oKey.sigBytes = iKey.sigBytes = hasherBlockSizeBytes;
	
		            // Set initial values
		            this.reset();
		        },
	
		        /**
		         * Resets this HMAC to its initial state.
		         *
		         * @example
		         *
		         *     hmacHasher.reset();
		         */
		        reset: function () {
		            // Shortcut
		            var hasher = this._hasher;
	
		            // Reset
		            hasher.reset();
		            hasher.update(this._iKey);
		        },
	
		        /**
		         * Updates this HMAC with a message.
		         *
		         * @param {WordArray|string} messageUpdate The message to append.
		         *
		         * @return {HMAC} This HMAC instance.
		         *
		         * @example
		         *
		         *     hmacHasher.update('message');
		         *     hmacHasher.update(wordArray);
		         */
		        update: function (messageUpdate) {
		            this._hasher.update(messageUpdate);
	
		            // Chainable
		            return this;
		        },
	
		        /**
		         * Finalizes the HMAC computation.
		         * Note that the finalize operation is effectively a destructive, read-once operation.
		         *
		         * @param {WordArray|string} messageUpdate (Optional) A final message update.
		         *
		         * @return {WordArray} The HMAC.
		         *
		         * @example
		         *
		         *     var hmac = hmacHasher.finalize();
		         *     var hmac = hmacHasher.finalize('message');
		         *     var hmac = hmacHasher.finalize(wordArray);
		         */
		        finalize: function (messageUpdate) {
		            // Shortcut
		            var hasher = this._hasher;
	
		            // Compute HMAC
		            var innerHash = hasher.finalize(messageUpdate);
		            hasher.reset();
		            var hmac = hasher.finalize(this._oKey.clone().concat(innerHash));
	
		            return hmac;
		        }
		    });
		}());
	
	
	}));

/***/ }),
/* 116 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(108), __webpack_require__(115));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./sha1", "./hmac"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		(function () {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var Base = C_lib.Base;
		    var WordArray = C_lib.WordArray;
		    var C_algo = C.algo;
		    var SHA1 = C_algo.SHA1;
		    var HMAC = C_algo.HMAC;
	
		    /**
		     * Password-Based Key Derivation Function 2 algorithm.
		     */
		    var PBKDF2 = C_algo.PBKDF2 = Base.extend({
		        /**
		         * Configuration options.
		         *
		         * @property {number} keySize The key size in words to generate. Default: 4 (128 bits)
		         * @property {Hasher} hasher The hasher to use. Default: SHA1
		         * @property {number} iterations The number of iterations to perform. Default: 1
		         */
		        cfg: Base.extend({
		            keySize: 128/32,
		            hasher: SHA1,
		            iterations: 1
		        }),
	
		        /**
		         * Initializes a newly created key derivation function.
		         *
		         * @param {Object} cfg (Optional) The configuration options to use for the derivation.
		         *
		         * @example
		         *
		         *     var kdf = CryptoJS.algo.PBKDF2.create();
		         *     var kdf = CryptoJS.algo.PBKDF2.create({ keySize: 8 });
		         *     var kdf = CryptoJS.algo.PBKDF2.create({ keySize: 8, iterations: 1000 });
		         */
		        init: function (cfg) {
		            this.cfg = this.cfg.extend(cfg);
		        },
	
		        /**
		         * Computes the Password-Based Key Derivation Function 2.
		         *
		         * @param {WordArray|string} password The password.
		         * @param {WordArray|string} salt A salt.
		         *
		         * @return {WordArray} The derived key.
		         *
		         * @example
		         *
		         *     var key = kdf.compute(password, salt);
		         */
		        compute: function (password, salt) {
		            // Shortcut
		            var cfg = this.cfg;
	
		            // Init HMAC
		            var hmac = HMAC.create(cfg.hasher, password);
	
		            // Initial values
		            var derivedKey = WordArray.create();
		            var blockIndex = WordArray.create([0x00000001]);
	
		            // Shortcuts
		            var derivedKeyWords = derivedKey.words;
		            var blockIndexWords = blockIndex.words;
		            var keySize = cfg.keySize;
		            var iterations = cfg.iterations;
	
		            // Generate key
		            while (derivedKeyWords.length < keySize) {
		                var block = hmac.update(salt).finalize(blockIndex);
		                hmac.reset();
	
		                // Shortcuts
		                var blockWords = block.words;
		                var blockWordsLength = blockWords.length;
	
		                // Iterations
		                var intermediate = block;
		                for (var i = 1; i < iterations; i++) {
		                    intermediate = hmac.finalize(intermediate);
		                    hmac.reset();
	
		                    // Shortcut
		                    var intermediateWords = intermediate.words;
	
		                    // XOR intermediate with block
		                    for (var j = 0; j < blockWordsLength; j++) {
		                        blockWords[j] ^= intermediateWords[j];
		                    }
		                }
	
		                derivedKey.concat(block);
		                blockIndexWords[0]++;
		            }
		            derivedKey.sigBytes = keySize * 4;
	
		            return derivedKey;
		        }
		    });
	
		    /**
		     * Computes the Password-Based Key Derivation Function 2.
		     *
		     * @param {WordArray|string} password The password.
		     * @param {WordArray|string} salt A salt.
		     * @param {Object} cfg (Optional) The configuration options to use for this computation.
		     *
		     * @return {WordArray} The derived key.
		     *
		     * @static
		     *
		     * @example
		     *
		     *     var key = CryptoJS.PBKDF2(password, salt);
		     *     var key = CryptoJS.PBKDF2(password, salt, { keySize: 8 });
		     *     var key = CryptoJS.PBKDF2(password, salt, { keySize: 8, iterations: 1000 });
		     */
		    C.PBKDF2 = function (password, salt, cfg) {
		        return PBKDF2.create(cfg).compute(password, salt);
		    };
		}());
	
	
		return CryptoJS.PBKDF2;
	
	}));

/***/ }),
/* 117 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(108), __webpack_require__(115));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./sha1", "./hmac"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		(function () {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var Base = C_lib.Base;
		    var WordArray = C_lib.WordArray;
		    var C_algo = C.algo;
		    var MD5 = C_algo.MD5;
	
		    /**
		     * This key derivation function is meant to conform with EVP_BytesToKey.
		     * www.openssl.org/docs/crypto/EVP_BytesToKey.html
		     */
		    var EvpKDF = C_algo.EvpKDF = Base.extend({
		        /**
		         * Configuration options.
		         *
		         * @property {number} keySize The key size in words to generate. Default: 4 (128 bits)
		         * @property {Hasher} hasher The hash algorithm to use. Default: MD5
		         * @property {number} iterations The number of iterations to perform. Default: 1
		         */
		        cfg: Base.extend({
		            keySize: 128/32,
		            hasher: MD5,
		            iterations: 1
		        }),
	
		        /**
		         * Initializes a newly created key derivation function.
		         *
		         * @param {Object} cfg (Optional) The configuration options to use for the derivation.
		         *
		         * @example
		         *
		         *     var kdf = CryptoJS.algo.EvpKDF.create();
		         *     var kdf = CryptoJS.algo.EvpKDF.create({ keySize: 8 });
		         *     var kdf = CryptoJS.algo.EvpKDF.create({ keySize: 8, iterations: 1000 });
		         */
		        init: function (cfg) {
		            this.cfg = this.cfg.extend(cfg);
		        },
	
		        /**
		         * Derives a key from a password.
		         *
		         * @param {WordArray|string} password The password.
		         * @param {WordArray|string} salt A salt.
		         *
		         * @return {WordArray} The derived key.
		         *
		         * @example
		         *
		         *     var key = kdf.compute(password, salt);
		         */
		        compute: function (password, salt) {
		            // Shortcut
		            var cfg = this.cfg;
	
		            // Init hasher
		            var hasher = cfg.hasher.create();
	
		            // Initial values
		            var derivedKey = WordArray.create();
	
		            // Shortcuts
		            var derivedKeyWords = derivedKey.words;
		            var keySize = cfg.keySize;
		            var iterations = cfg.iterations;
	
		            // Generate key
		            while (derivedKeyWords.length < keySize) {
		                if (block) {
		                    hasher.update(block);
		                }
		                var block = hasher.update(password).finalize(salt);
		                hasher.reset();
	
		                // Iterations
		                for (var i = 1; i < iterations; i++) {
		                    block = hasher.finalize(block);
		                    hasher.reset();
		                }
	
		                derivedKey.concat(block);
		            }
		            derivedKey.sigBytes = keySize * 4;
	
		            return derivedKey;
		        }
		    });
	
		    /**
		     * Derives a key from a password.
		     *
		     * @param {WordArray|string} password The password.
		     * @param {WordArray|string} salt A salt.
		     * @param {Object} cfg (Optional) The configuration options to use for this computation.
		     *
		     * @return {WordArray} The derived key.
		     *
		     * @static
		     *
		     * @example
		     *
		     *     var key = CryptoJS.EvpKDF(password, salt);
		     *     var key = CryptoJS.EvpKDF(password, salt, { keySize: 8 });
		     *     var key = CryptoJS.EvpKDF(password, salt, { keySize: 8, iterations: 1000 });
		     */
		    C.EvpKDF = function (password, salt, cfg) {
		        return EvpKDF.create(cfg).compute(password, salt);
		    };
		}());
	
	
		return CryptoJS.EvpKDF;
	
	}));

/***/ }),
/* 118 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		/**
		 * Cipher core components.
		 */
		CryptoJS.lib.Cipher || (function (undefined) {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var Base = C_lib.Base;
		    var WordArray = C_lib.WordArray;
		    var BufferedBlockAlgorithm = C_lib.BufferedBlockAlgorithm;
		    var C_enc = C.enc;
		    var Utf8 = C_enc.Utf8;
		    var Base64 = C_enc.Base64;
		    var C_algo = C.algo;
		    var EvpKDF = C_algo.EvpKDF;
	
		    /**
		     * Abstract base cipher template.
		     *
		     * @property {number} keySize This cipher's key size. Default: 4 (128 bits)
		     * @property {number} ivSize This cipher's IV size. Default: 4 (128 bits)
		     * @property {number} _ENC_XFORM_MODE A constant representing encryption mode.
		     * @property {number} _DEC_XFORM_MODE A constant representing decryption mode.
		     */
		    var Cipher = C_lib.Cipher = BufferedBlockAlgorithm.extend({
		        /**
		         * Configuration options.
		         *
		         * @property {WordArray} iv The IV to use for this operation.
		         */
		        cfg: Base.extend(),
	
		        /**
		         * Creates this cipher in encryption mode.
		         *
		         * @param {WordArray} key The key.
		         * @param {Object} cfg (Optional) The configuration options to use for this operation.
		         *
		         * @return {Cipher} A cipher instance.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var cipher = CryptoJS.algo.AES.createEncryptor(keyWordArray, { iv: ivWordArray });
		         */
		        createEncryptor: function (key, cfg) {
		            return this.create(this._ENC_XFORM_MODE, key, cfg);
		        },
	
		        /**
		         * Creates this cipher in decryption mode.
		         *
		         * @param {WordArray} key The key.
		         * @param {Object} cfg (Optional) The configuration options to use for this operation.
		         *
		         * @return {Cipher} A cipher instance.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var cipher = CryptoJS.algo.AES.createDecryptor(keyWordArray, { iv: ivWordArray });
		         */
		        createDecryptor: function (key, cfg) {
		            return this.create(this._DEC_XFORM_MODE, key, cfg);
		        },
	
		        /**
		         * Initializes a newly created cipher.
		         *
		         * @param {number} xformMode Either the encryption or decryption transormation mode constant.
		         * @param {WordArray} key The key.
		         * @param {Object} cfg (Optional) The configuration options to use for this operation.
		         *
		         * @example
		         *
		         *     var cipher = CryptoJS.algo.AES.create(CryptoJS.algo.AES._ENC_XFORM_MODE, keyWordArray, { iv: ivWordArray });
		         */
		        init: function (xformMode, key, cfg) {
		            // Apply config defaults
		            this.cfg = this.cfg.extend(cfg);
	
		            // Store transform mode and key
		            this._xformMode = xformMode;
		            this._key = key;
	
		            // Set initial values
		            this.reset();
		        },
	
		        /**
		         * Resets this cipher to its initial state.
		         *
		         * @example
		         *
		         *     cipher.reset();
		         */
		        reset: function () {
		            // Reset data buffer
		            BufferedBlockAlgorithm.reset.call(this);
	
		            // Perform concrete-cipher logic
		            this._doReset();
		        },
	
		        /**
		         * Adds data to be encrypted or decrypted.
		         *
		         * @param {WordArray|string} dataUpdate The data to encrypt or decrypt.
		         *
		         * @return {WordArray} The data after processing.
		         *
		         * @example
		         *
		         *     var encrypted = cipher.process('data');
		         *     var encrypted = cipher.process(wordArray);
		         */
		        process: function (dataUpdate) {
		            // Append
		            this._append(dataUpdate);
	
		            // Process available blocks
		            return this._process();
		        },
	
		        /**
		         * Finalizes the encryption or decryption process.
		         * Note that the finalize operation is effectively a destructive, read-once operation.
		         *
		         * @param {WordArray|string} dataUpdate The final data to encrypt or decrypt.
		         *
		         * @return {WordArray} The data after final processing.
		         *
		         * @example
		         *
		         *     var encrypted = cipher.finalize();
		         *     var encrypted = cipher.finalize('data');
		         *     var encrypted = cipher.finalize(wordArray);
		         */
		        finalize: function (dataUpdate) {
		            // Final data update
		            if (dataUpdate) {
		                this._append(dataUpdate);
		            }
	
		            // Perform concrete-cipher logic
		            var finalProcessedData = this._doFinalize();
	
		            return finalProcessedData;
		        },
	
		        keySize: 128/32,
	
		        ivSize: 128/32,
	
		        _ENC_XFORM_MODE: 1,
	
		        _DEC_XFORM_MODE: 2,
	
		        /**
		         * Creates shortcut functions to a cipher's object interface.
		         *
		         * @param {Cipher} cipher The cipher to create a helper for.
		         *
		         * @return {Object} An object with encrypt and decrypt shortcut functions.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var AES = CryptoJS.lib.Cipher._createHelper(CryptoJS.algo.AES);
		         */
		        _createHelper: (function () {
		            function selectCipherStrategy(key) {
		                if (typeof key == 'string') {
		                    return PasswordBasedCipher;
		                } else {
		                    return SerializableCipher;
		                }
		            }
	
		            return function (cipher) {
		                return {
		                    encrypt: function (message, key, cfg) {
		                        return selectCipherStrategy(key).encrypt(cipher, message, key, cfg);
		                    },
	
		                    decrypt: function (ciphertext, key, cfg) {
		                        return selectCipherStrategy(key).decrypt(cipher, ciphertext, key, cfg);
		                    }
		                };
		            };
		        }())
		    });
	
		    /**
		     * Abstract base stream cipher template.
		     *
		     * @property {number} blockSize The number of 32-bit words this cipher operates on. Default: 1 (32 bits)
		     */
		    var StreamCipher = C_lib.StreamCipher = Cipher.extend({
		        _doFinalize: function () {
		            // Process partial blocks
		            var finalProcessedBlocks = this._process(!!'flush');
	
		            return finalProcessedBlocks;
		        },
	
		        blockSize: 1
		    });
	
		    /**
		     * Mode namespace.
		     */
		    var C_mode = C.mode = {};
	
		    /**
		     * Abstract base block cipher mode template.
		     */
		    var BlockCipherMode = C_lib.BlockCipherMode = Base.extend({
		        /**
		         * Creates this mode for encryption.
		         *
		         * @param {Cipher} cipher A block cipher instance.
		         * @param {Array} iv The IV words.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var mode = CryptoJS.mode.CBC.createEncryptor(cipher, iv.words);
		         */
		        createEncryptor: function (cipher, iv) {
		            return this.Encryptor.create(cipher, iv);
		        },
	
		        /**
		         * Creates this mode for decryption.
		         *
		         * @param {Cipher} cipher A block cipher instance.
		         * @param {Array} iv The IV words.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var mode = CryptoJS.mode.CBC.createDecryptor(cipher, iv.words);
		         */
		        createDecryptor: function (cipher, iv) {
		            return this.Decryptor.create(cipher, iv);
		        },
	
		        /**
		         * Initializes a newly created mode.
		         *
		         * @param {Cipher} cipher A block cipher instance.
		         * @param {Array} iv The IV words.
		         *
		         * @example
		         *
		         *     var mode = CryptoJS.mode.CBC.Encryptor.create(cipher, iv.words);
		         */
		        init: function (cipher, iv) {
		            this._cipher = cipher;
		            this._iv = iv;
		        }
		    });
	
		    /**
		     * Cipher Block Chaining mode.
		     */
		    var CBC = C_mode.CBC = (function () {
		        /**
		         * Abstract base CBC mode.
		         */
		        var CBC = BlockCipherMode.extend();
	
		        /**
		         * CBC encryptor.
		         */
		        CBC.Encryptor = CBC.extend({
		            /**
		             * Processes the data block at offset.
		             *
		             * @param {Array} words The data words to operate on.
		             * @param {number} offset The offset where the block starts.
		             *
		             * @example
		             *
		             *     mode.processBlock(data.words, offset);
		             */
		            processBlock: function (words, offset) {
		                // Shortcuts
		                var cipher = this._cipher;
		                var blockSize = cipher.blockSize;
	
		                // XOR and encrypt
		                xorBlock.call(this, words, offset, blockSize);
		                cipher.encryptBlock(words, offset);
	
		                // Remember this block to use with next block
		                this._prevBlock = words.slice(offset, offset + blockSize);
		            }
		        });
	
		        /**
		         * CBC decryptor.
		         */
		        CBC.Decryptor = CBC.extend({
		            /**
		             * Processes the data block at offset.
		             *
		             * @param {Array} words The data words to operate on.
		             * @param {number} offset The offset where the block starts.
		             *
		             * @example
		             *
		             *     mode.processBlock(data.words, offset);
		             */
		            processBlock: function (words, offset) {
		                // Shortcuts
		                var cipher = this._cipher;
		                var blockSize = cipher.blockSize;
	
		                // Remember this block to use with next block
		                var thisBlock = words.slice(offset, offset + blockSize);
	
		                // Decrypt and XOR
		                cipher.decryptBlock(words, offset);
		                xorBlock.call(this, words, offset, blockSize);
	
		                // This block becomes the previous block
		                this._prevBlock = thisBlock;
		            }
		        });
	
		        function xorBlock(words, offset, blockSize) {
		            // Shortcut
		            var iv = this._iv;
	
		            // Choose mixing block
		            if (iv) {
		                var block = iv;
	
		                // Remove IV for subsequent blocks
		                this._iv = undefined;
		            } else {
		                var block = this._prevBlock;
		            }
	
		            // XOR blocks
		            for (var i = 0; i < blockSize; i++) {
		                words[offset + i] ^= block[i];
		            }
		        }
	
		        return CBC;
		    }());
	
		    /**
		     * Padding namespace.
		     */
		    var C_pad = C.pad = {};
	
		    /**
		     * PKCS #5/7 padding strategy.
		     */
		    var Pkcs7 = C_pad.Pkcs7 = {
		        /**
		         * Pads data using the algorithm defined in PKCS #5/7.
		         *
		         * @param {WordArray} data The data to pad.
		         * @param {number} blockSize The multiple that the data should be padded to.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     CryptoJS.pad.Pkcs7.pad(wordArray, 4);
		         */
		        pad: function (data, blockSize) {
		            // Shortcut
		            var blockSizeBytes = blockSize * 4;
	
		            // Count padding bytes
		            var nPaddingBytes = blockSizeBytes - data.sigBytes % blockSizeBytes;
	
		            // Create padding word
		            var paddingWord = (nPaddingBytes << 24) | (nPaddingBytes << 16) | (nPaddingBytes << 8) | nPaddingBytes;
	
		            // Create padding
		            var paddingWords = [];
		            for (var i = 0; i < nPaddingBytes; i += 4) {
		                paddingWords.push(paddingWord);
		            }
		            var padding = WordArray.create(paddingWords, nPaddingBytes);
	
		            // Add padding
		            data.concat(padding);
		        },
	
		        /**
		         * Unpads data that had been padded using the algorithm defined in PKCS #5/7.
		         *
		         * @param {WordArray} data The data to unpad.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     CryptoJS.pad.Pkcs7.unpad(wordArray);
		         */
		        unpad: function (data) {
		            // Get number of padding bytes from last byte
		            var nPaddingBytes = data.words[(data.sigBytes - 1) >>> 2] & 0xff;
	
		            // Remove padding
		            data.sigBytes -= nPaddingBytes;
		        }
		    };
	
		    /**
		     * Abstract base block cipher template.
		     *
		     * @property {number} blockSize The number of 32-bit words this cipher operates on. Default: 4 (128 bits)
		     */
		    var BlockCipher = C_lib.BlockCipher = Cipher.extend({
		        /**
		         * Configuration options.
		         *
		         * @property {Mode} mode The block mode to use. Default: CBC
		         * @property {Padding} padding The padding strategy to use. Default: Pkcs7
		         */
		        cfg: Cipher.cfg.extend({
		            mode: CBC,
		            padding: Pkcs7
		        }),
	
		        reset: function () {
		            // Reset cipher
		            Cipher.reset.call(this);
	
		            // Shortcuts
		            var cfg = this.cfg;
		            var iv = cfg.iv;
		            var mode = cfg.mode;
	
		            // Reset block mode
		            if (this._xformMode == this._ENC_XFORM_MODE) {
		                var modeCreator = mode.createEncryptor;
		            } else /* if (this._xformMode == this._DEC_XFORM_MODE) */ {
		                var modeCreator = mode.createDecryptor;
	
		                // Keep at least one block in the buffer for unpadding
		                this._minBufferSize = 1;
		            }
		            this._mode = modeCreator.call(mode, this, iv && iv.words);
		        },
	
		        _doProcessBlock: function (words, offset) {
		            this._mode.processBlock(words, offset);
		        },
	
		        _doFinalize: function () {
		            // Shortcut
		            var padding = this.cfg.padding;
	
		            // Finalize
		            if (this._xformMode == this._ENC_XFORM_MODE) {
		                // Pad data
		                padding.pad(this._data, this.blockSize);
	
		                // Process final blocks
		                var finalProcessedBlocks = this._process(!!'flush');
		            } else /* if (this._xformMode == this._DEC_XFORM_MODE) */ {
		                // Process final blocks
		                var finalProcessedBlocks = this._process(!!'flush');
	
		                // Unpad data
		                padding.unpad(finalProcessedBlocks);
		            }
	
		            return finalProcessedBlocks;
		        },
	
		        blockSize: 128/32
		    });
	
		    /**
		     * A collection of cipher parameters.
		     *
		     * @property {WordArray} ciphertext The raw ciphertext.
		     * @property {WordArray} key The key to this ciphertext.
		     * @property {WordArray} iv The IV used in the ciphering operation.
		     * @property {WordArray} salt The salt used with a key derivation function.
		     * @property {Cipher} algorithm The cipher algorithm.
		     * @property {Mode} mode The block mode used in the ciphering operation.
		     * @property {Padding} padding The padding scheme used in the ciphering operation.
		     * @property {number} blockSize The block size of the cipher.
		     * @property {Format} formatter The default formatting strategy to convert this cipher params object to a string.
		     */
		    var CipherParams = C_lib.CipherParams = Base.extend({
		        /**
		         * Initializes a newly created cipher params object.
		         *
		         * @param {Object} cipherParams An object with any of the possible cipher parameters.
		         *
		         * @example
		         *
		         *     var cipherParams = CryptoJS.lib.CipherParams.create({
		         *         ciphertext: ciphertextWordArray,
		         *         key: keyWordArray,
		         *         iv: ivWordArray,
		         *         salt: saltWordArray,
		         *         algorithm: CryptoJS.algo.AES,
		         *         mode: CryptoJS.mode.CBC,
		         *         padding: CryptoJS.pad.PKCS7,
		         *         blockSize: 4,
		         *         formatter: CryptoJS.format.OpenSSL
		         *     });
		         */
		        init: function (cipherParams) {
		            this.mixIn(cipherParams);
		        },
	
		        /**
		         * Converts this cipher params object to a string.
		         *
		         * @param {Format} formatter (Optional) The formatting strategy to use.
		         *
		         * @return {string} The stringified cipher params.
		         *
		         * @throws Error If neither the formatter nor the default formatter is set.
		         *
		         * @example
		         *
		         *     var string = cipherParams + '';
		         *     var string = cipherParams.toString();
		         *     var string = cipherParams.toString(CryptoJS.format.OpenSSL);
		         */
		        toString: function (formatter) {
		            return (formatter || this.formatter).stringify(this);
		        }
		    });
	
		    /**
		     * Format namespace.
		     */
		    var C_format = C.format = {};
	
		    /**
		     * OpenSSL formatting strategy.
		     */
		    var OpenSSLFormatter = C_format.OpenSSL = {
		        /**
		         * Converts a cipher params object to an OpenSSL-compatible string.
		         *
		         * @param {CipherParams} cipherParams The cipher params object.
		         *
		         * @return {string} The OpenSSL-compatible string.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var openSSLString = CryptoJS.format.OpenSSL.stringify(cipherParams);
		         */
		        stringify: function (cipherParams) {
		            // Shortcuts
		            var ciphertext = cipherParams.ciphertext;
		            var salt = cipherParams.salt;
	
		            // Format
		            if (salt) {
		                var wordArray = WordArray.create([0x53616c74, 0x65645f5f]).concat(salt).concat(ciphertext);
		            } else {
		                var wordArray = ciphertext;
		            }
	
		            return wordArray.toString(Base64);
		        },
	
		        /**
		         * Converts an OpenSSL-compatible string to a cipher params object.
		         *
		         * @param {string} openSSLStr The OpenSSL-compatible string.
		         *
		         * @return {CipherParams} The cipher params object.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var cipherParams = CryptoJS.format.OpenSSL.parse(openSSLString);
		         */
		        parse: function (openSSLStr) {
		            // Parse base64
		            var ciphertext = Base64.parse(openSSLStr);
	
		            // Shortcut
		            var ciphertextWords = ciphertext.words;
	
		            // Test for salt
		            if (ciphertextWords[0] == 0x53616c74 && ciphertextWords[1] == 0x65645f5f) {
		                // Extract salt
		                var salt = WordArray.create(ciphertextWords.slice(2, 4));
	
		                // Remove salt from ciphertext
		                ciphertextWords.splice(0, 4);
		                ciphertext.sigBytes -= 16;
		            }
	
		            return CipherParams.create({ ciphertext: ciphertext, salt: salt });
		        }
		    };
	
		    /**
		     * A cipher wrapper that returns ciphertext as a serializable cipher params object.
		     */
		    var SerializableCipher = C_lib.SerializableCipher = Base.extend({
		        /**
		         * Configuration options.
		         *
		         * @property {Formatter} format The formatting strategy to convert cipher param objects to and from a string. Default: OpenSSL
		         */
		        cfg: Base.extend({
		            format: OpenSSLFormatter
		        }),
	
		        /**
		         * Encrypts a message.
		         *
		         * @param {Cipher} cipher The cipher algorithm to use.
		         * @param {WordArray|string} message The message to encrypt.
		         * @param {WordArray} key The key.
		         * @param {Object} cfg (Optional) The configuration options to use for this operation.
		         *
		         * @return {CipherParams} A cipher params object.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var ciphertextParams = CryptoJS.lib.SerializableCipher.encrypt(CryptoJS.algo.AES, message, key);
		         *     var ciphertextParams = CryptoJS.lib.SerializableCipher.encrypt(CryptoJS.algo.AES, message, key, { iv: iv });
		         *     var ciphertextParams = CryptoJS.lib.SerializableCipher.encrypt(CryptoJS.algo.AES, message, key, { iv: iv, format: CryptoJS.format.OpenSSL });
		         */
		        encrypt: function (cipher, message, key, cfg) {
		            // Apply config defaults
		            cfg = this.cfg.extend(cfg);
	
		            // Encrypt
		            var encryptor = cipher.createEncryptor(key, cfg);
		            var ciphertext = encryptor.finalize(message);
	
		            // Shortcut
		            var cipherCfg = encryptor.cfg;
	
		            // Create and return serializable cipher params
		            return CipherParams.create({
		                ciphertext: ciphertext,
		                key: key,
		                iv: cipherCfg.iv,
		                algorithm: cipher,
		                mode: cipherCfg.mode,
		                padding: cipherCfg.padding,
		                blockSize: cipher.blockSize,
		                formatter: cfg.format
		            });
		        },
	
		        /**
		         * Decrypts serialized ciphertext.
		         *
		         * @param {Cipher} cipher The cipher algorithm to use.
		         * @param {CipherParams|string} ciphertext The ciphertext to decrypt.
		         * @param {WordArray} key The key.
		         * @param {Object} cfg (Optional) The configuration options to use for this operation.
		         *
		         * @return {WordArray} The plaintext.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var plaintext = CryptoJS.lib.SerializableCipher.decrypt(CryptoJS.algo.AES, formattedCiphertext, key, { iv: iv, format: CryptoJS.format.OpenSSL });
		         *     var plaintext = CryptoJS.lib.SerializableCipher.decrypt(CryptoJS.algo.AES, ciphertextParams, key, { iv: iv, format: CryptoJS.format.OpenSSL });
		         */
		        decrypt: function (cipher, ciphertext, key, cfg) {
		            // Apply config defaults
		            cfg = this.cfg.extend(cfg);
	
		            // Convert string to CipherParams
		            ciphertext = this._parse(ciphertext, cfg.format);
	
		            // Decrypt
		            var plaintext = cipher.createDecryptor(key, cfg).finalize(ciphertext.ciphertext);
	
		            return plaintext;
		        },
	
		        /**
		         * Converts serialized ciphertext to CipherParams,
		         * else assumed CipherParams already and returns ciphertext unchanged.
		         *
		         * @param {CipherParams|string} ciphertext The ciphertext.
		         * @param {Formatter} format The formatting strategy to use to parse serialized ciphertext.
		         *
		         * @return {CipherParams} The unserialized ciphertext.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var ciphertextParams = CryptoJS.lib.SerializableCipher._parse(ciphertextStringOrParams, format);
		         */
		        _parse: function (ciphertext, format) {
		            if (typeof ciphertext == 'string') {
		                return format.parse(ciphertext, this);
		            } else {
		                return ciphertext;
		            }
		        }
		    });
	
		    /**
		     * Key derivation function namespace.
		     */
		    var C_kdf = C.kdf = {};
	
		    /**
		     * OpenSSL key derivation function.
		     */
		    var OpenSSLKdf = C_kdf.OpenSSL = {
		        /**
		         * Derives a key and IV from a password.
		         *
		         * @param {string} password The password to derive from.
		         * @param {number} keySize The size in words of the key to generate.
		         * @param {number} ivSize The size in words of the IV to generate.
		         * @param {WordArray|string} salt (Optional) A 64-bit salt to use. If omitted, a salt will be generated randomly.
		         *
		         * @return {CipherParams} A cipher params object with the key, IV, and salt.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var derivedParams = CryptoJS.kdf.OpenSSL.execute('Password', 256/32, 128/32);
		         *     var derivedParams = CryptoJS.kdf.OpenSSL.execute('Password', 256/32, 128/32, 'saltsalt');
		         */
		        execute: function (password, keySize, ivSize, salt) {
		            // Generate random salt
		            if (!salt) {
		                salt = WordArray.random(64/8);
		            }
	
		            // Derive key and IV
		            var key = EvpKDF.create({ keySize: keySize + ivSize }).compute(password, salt);
	
		            // Separate key and IV
		            var iv = WordArray.create(key.words.slice(keySize), ivSize * 4);
		            key.sigBytes = keySize * 4;
	
		            // Return params
		            return CipherParams.create({ key: key, iv: iv, salt: salt });
		        }
		    };
	
		    /**
		     * A serializable cipher wrapper that derives the key from a password,
		     * and returns ciphertext as a serializable cipher params object.
		     */
		    var PasswordBasedCipher = C_lib.PasswordBasedCipher = SerializableCipher.extend({
		        /**
		         * Configuration options.
		         *
		         * @property {KDF} kdf The key derivation function to use to generate a key and IV from a password. Default: OpenSSL
		         */
		        cfg: SerializableCipher.cfg.extend({
		            kdf: OpenSSLKdf
		        }),
	
		        /**
		         * Encrypts a message using a password.
		         *
		         * @param {Cipher} cipher The cipher algorithm to use.
		         * @param {WordArray|string} message The message to encrypt.
		         * @param {string} password The password.
		         * @param {Object} cfg (Optional) The configuration options to use for this operation.
		         *
		         * @return {CipherParams} A cipher params object.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var ciphertextParams = CryptoJS.lib.PasswordBasedCipher.encrypt(CryptoJS.algo.AES, message, 'password');
		         *     var ciphertextParams = CryptoJS.lib.PasswordBasedCipher.encrypt(CryptoJS.algo.AES, message, 'password', { format: CryptoJS.format.OpenSSL });
		         */
		        encrypt: function (cipher, message, password, cfg) {
		            // Apply config defaults
		            cfg = this.cfg.extend(cfg);
	
		            // Derive key and other params
		            var derivedParams = cfg.kdf.execute(password, cipher.keySize, cipher.ivSize);
	
		            // Add IV to config
		            cfg.iv = derivedParams.iv;
	
		            // Encrypt
		            var ciphertext = SerializableCipher.encrypt.call(this, cipher, message, derivedParams.key, cfg);
	
		            // Mix in derived params
		            ciphertext.mixIn(derivedParams);
	
		            return ciphertext;
		        },
	
		        /**
		         * Decrypts serialized ciphertext using a password.
		         *
		         * @param {Cipher} cipher The cipher algorithm to use.
		         * @param {CipherParams|string} ciphertext The ciphertext to decrypt.
		         * @param {string} password The password.
		         * @param {Object} cfg (Optional) The configuration options to use for this operation.
		         *
		         * @return {WordArray} The plaintext.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var plaintext = CryptoJS.lib.PasswordBasedCipher.decrypt(CryptoJS.algo.AES, formattedCiphertext, 'password', { format: CryptoJS.format.OpenSSL });
		         *     var plaintext = CryptoJS.lib.PasswordBasedCipher.decrypt(CryptoJS.algo.AES, ciphertextParams, 'password', { format: CryptoJS.format.OpenSSL });
		         */
		        decrypt: function (cipher, ciphertext, password, cfg) {
		            // Apply config defaults
		            cfg = this.cfg.extend(cfg);
	
		            // Convert string to CipherParams
		            ciphertext = this._parse(ciphertext, cfg.format);
	
		            // Derive key and other params
		            var derivedParams = cfg.kdf.execute(password, cipher.keySize, cipher.ivSize, ciphertext.salt);
	
		            // Add IV to config
		            cfg.iv = derivedParams.iv;
	
		            // Decrypt
		            var plaintext = SerializableCipher.decrypt.call(this, cipher, ciphertext, derivedParams.key, cfg);
	
		            return plaintext;
		        }
		    });
		}());
	
	
	}));

/***/ }),
/* 119 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(118));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./cipher-core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		/**
		 * Cipher Feedback block mode.
		 */
		CryptoJS.mode.CFB = (function () {
		    var CFB = CryptoJS.lib.BlockCipherMode.extend();
	
		    CFB.Encryptor = CFB.extend({
		        processBlock: function (words, offset) {
		            // Shortcuts
		            var cipher = this._cipher;
		            var blockSize = cipher.blockSize;
	
		            generateKeystreamAndEncrypt.call(this, words, offset, blockSize, cipher);
	
		            // Remember this block to use with next block
		            this._prevBlock = words.slice(offset, offset + blockSize);
		        }
		    });
	
		    CFB.Decryptor = CFB.extend({
		        processBlock: function (words, offset) {
		            // Shortcuts
		            var cipher = this._cipher;
		            var blockSize = cipher.blockSize;
	
		            // Remember this block to use with next block
		            var thisBlock = words.slice(offset, offset + blockSize);
	
		            generateKeystreamAndEncrypt.call(this, words, offset, blockSize, cipher);
	
		            // This block becomes the previous block
		            this._prevBlock = thisBlock;
		        }
		    });
	
		    function generateKeystreamAndEncrypt(words, offset, blockSize, cipher) {
		        // Shortcut
		        var iv = this._iv;
	
		        // Generate keystream
		        if (iv) {
		            var keystream = iv.slice(0);
	
		            // Remove IV for subsequent blocks
		            this._iv = undefined;
		        } else {
		            var keystream = this._prevBlock;
		        }
		        cipher.encryptBlock(keystream, 0);
	
		        // Encrypt
		        for (var i = 0; i < blockSize; i++) {
		            words[offset + i] ^= keystream[i];
		        }
		    }
	
		    return CFB;
		}());
	
	
		return CryptoJS.mode.CFB;
	
	}));

/***/ }),
/* 120 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(118));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./cipher-core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		/**
		 * Counter block mode.
		 */
		CryptoJS.mode.CTR = (function () {
		    var CTR = CryptoJS.lib.BlockCipherMode.extend();
	
		    var Encryptor = CTR.Encryptor = CTR.extend({
		        processBlock: function (words, offset) {
		            // Shortcuts
		            var cipher = this._cipher
		            var blockSize = cipher.blockSize;
		            var iv = this._iv;
		            var counter = this._counter;
	
		            // Generate keystream
		            if (iv) {
		                counter = this._counter = iv.slice(0);
	
		                // Remove IV for subsequent blocks
		                this._iv = undefined;
		            }
		            var keystream = counter.slice(0);
		            cipher.encryptBlock(keystream, 0);
	
		            // Increment counter
		            counter[blockSize - 1] = (counter[blockSize - 1] + 1) | 0
	
		            // Encrypt
		            for (var i = 0; i < blockSize; i++) {
		                words[offset + i] ^= keystream[i];
		            }
		        }
		    });
	
		    CTR.Decryptor = Encryptor;
	
		    return CTR;
		}());
	
	
		return CryptoJS.mode.CTR;
	
	}));

/***/ }),
/* 121 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(118));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./cipher-core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		/** @preserve
		 * Counter block mode compatible with  Dr Brian Gladman fileenc.c
		 * derived from CryptoJS.mode.CTR
		 * Jan Hruby jhruby.web@gmail.com
		 */
		CryptoJS.mode.CTRGladman = (function () {
		    var CTRGladman = CryptoJS.lib.BlockCipherMode.extend();
	
			function incWord(word)
			{
				if (((word >> 24) & 0xff) === 0xff) { //overflow
				var b1 = (word >> 16)&0xff;
				var b2 = (word >> 8)&0xff;
				var b3 = word & 0xff;
	
				if (b1 === 0xff) // overflow b1
				{
				b1 = 0;
				if (b2 === 0xff)
				{
					b2 = 0;
					if (b3 === 0xff)
					{
						b3 = 0;
					}
					else
					{
						++b3;
					}
				}
				else
				{
					++b2;
				}
				}
				else
				{
				++b1;
				}
	
				word = 0;
				word += (b1 << 16);
				word += (b2 << 8);
				word += b3;
				}
				else
				{
				word += (0x01 << 24);
				}
				return word;
			}
	
			function incCounter(counter)
			{
				if ((counter[0] = incWord(counter[0])) === 0)
				{
					// encr_data in fileenc.c from  Dr Brian Gladman's counts only with DWORD j < 8
					counter[1] = incWord(counter[1]);
				}
				return counter;
			}
	
		    var Encryptor = CTRGladman.Encryptor = CTRGladman.extend({
		        processBlock: function (words, offset) {
		            // Shortcuts
		            var cipher = this._cipher
		            var blockSize = cipher.blockSize;
		            var iv = this._iv;
		            var counter = this._counter;
	
		            // Generate keystream
		            if (iv) {
		                counter = this._counter = iv.slice(0);
	
		                // Remove IV for subsequent blocks
		                this._iv = undefined;
		            }
	
					incCounter(counter);
	
					var keystream = counter.slice(0);
		            cipher.encryptBlock(keystream, 0);
	
		            // Encrypt
		            for (var i = 0; i < blockSize; i++) {
		                words[offset + i] ^= keystream[i];
		            }
		        }
		    });
	
		    CTRGladman.Decryptor = Encryptor;
	
		    return CTRGladman;
		}());
	
	
	
	
		return CryptoJS.mode.CTRGladman;
	
	}));

/***/ }),
/* 122 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(118));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./cipher-core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		/**
		 * Output Feedback block mode.
		 */
		CryptoJS.mode.OFB = (function () {
		    var OFB = CryptoJS.lib.BlockCipherMode.extend();
	
		    var Encryptor = OFB.Encryptor = OFB.extend({
		        processBlock: function (words, offset) {
		            // Shortcuts
		            var cipher = this._cipher
		            var blockSize = cipher.blockSize;
		            var iv = this._iv;
		            var keystream = this._keystream;
	
		            // Generate keystream
		            if (iv) {
		                keystream = this._keystream = iv.slice(0);
	
		                // Remove IV for subsequent blocks
		                this._iv = undefined;
		            }
		            cipher.encryptBlock(keystream, 0);
	
		            // Encrypt
		            for (var i = 0; i < blockSize; i++) {
		                words[offset + i] ^= keystream[i];
		            }
		        }
		    });
	
		    OFB.Decryptor = Encryptor;
	
		    return OFB;
		}());
	
	
		return CryptoJS.mode.OFB;
	
	}));

/***/ }),
/* 123 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(118));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./cipher-core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		/**
		 * Electronic Codebook block mode.
		 */
		CryptoJS.mode.ECB = (function () {
		    var ECB = CryptoJS.lib.BlockCipherMode.extend();
	
		    ECB.Encryptor = ECB.extend({
		        processBlock: function (words, offset) {
		            this._cipher.encryptBlock(words, offset);
		        }
		    });
	
		    ECB.Decryptor = ECB.extend({
		        processBlock: function (words, offset) {
		            this._cipher.decryptBlock(words, offset);
		        }
		    });
	
		    return ECB;
		}());
	
	
		return CryptoJS.mode.ECB;
	
	}));

/***/ }),
/* 124 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(118));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./cipher-core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		/**
		 * ANSI X.923 padding strategy.
		 */
		CryptoJS.pad.AnsiX923 = {
		    pad: function (data, blockSize) {
		        // Shortcuts
		        var dataSigBytes = data.sigBytes;
		        var blockSizeBytes = blockSize * 4;
	
		        // Count padding bytes
		        var nPaddingBytes = blockSizeBytes - dataSigBytes % blockSizeBytes;
	
		        // Compute last byte position
		        var lastBytePos = dataSigBytes + nPaddingBytes - 1;
	
		        // Pad
		        data.clamp();
		        data.words[lastBytePos >>> 2] |= nPaddingBytes << (24 - (lastBytePos % 4) * 8);
		        data.sigBytes += nPaddingBytes;
		    },
	
		    unpad: function (data) {
		        // Get number of padding bytes from last byte
		        var nPaddingBytes = data.words[(data.sigBytes - 1) >>> 2] & 0xff;
	
		        // Remove padding
		        data.sigBytes -= nPaddingBytes;
		    }
		};
	
	
		return CryptoJS.pad.Ansix923;
	
	}));

/***/ }),
/* 125 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(118));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./cipher-core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		/**
		 * ISO 10126 padding strategy.
		 */
		CryptoJS.pad.Iso10126 = {
		    pad: function (data, blockSize) {
		        // Shortcut
		        var blockSizeBytes = blockSize * 4;
	
		        // Count padding bytes
		        var nPaddingBytes = blockSizeBytes - data.sigBytes % blockSizeBytes;
	
		        // Pad
		        data.concat(CryptoJS.lib.WordArray.random(nPaddingBytes - 1)).
		             concat(CryptoJS.lib.WordArray.create([nPaddingBytes << 24], 1));
		    },
	
		    unpad: function (data) {
		        // Get number of padding bytes from last byte
		        var nPaddingBytes = data.words[(data.sigBytes - 1) >>> 2] & 0xff;
	
		        // Remove padding
		        data.sigBytes -= nPaddingBytes;
		    }
		};
	
	
		return CryptoJS.pad.Iso10126;
	
	}));

/***/ }),
/* 126 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(118));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./cipher-core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		/**
		 * ISO/IEC 9797-1 Padding Method 2.
		 */
		CryptoJS.pad.Iso97971 = {
		    pad: function (data, blockSize) {
		        // Add 0x80 byte
		        data.concat(CryptoJS.lib.WordArray.create([0x80000000], 1));
	
		        // Zero pad the rest
		        CryptoJS.pad.ZeroPadding.pad(data, blockSize);
		    },
	
		    unpad: function (data) {
		        // Remove zero padding
		        CryptoJS.pad.ZeroPadding.unpad(data);
	
		        // Remove one more byte -- the 0x80 byte
		        data.sigBytes--;
		    }
		};
	
	
		return CryptoJS.pad.Iso97971;
	
	}));

/***/ }),
/* 127 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(118));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./cipher-core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		/**
		 * Zero padding strategy.
		 */
		CryptoJS.pad.ZeroPadding = {
		    pad: function (data, blockSize) {
		        // Shortcut
		        var blockSizeBytes = blockSize * 4;
	
		        // Pad
		        data.clamp();
		        data.sigBytes += blockSizeBytes - ((data.sigBytes % blockSizeBytes) || blockSizeBytes);
		    },
	
		    unpad: function (data) {
		        // Shortcut
		        var dataWords = data.words;
	
		        // Unpad
		        var i = data.sigBytes - 1;
		        while (!((dataWords[i >>> 2] >>> (24 - (i % 4) * 8)) & 0xff)) {
		            i--;
		        }
		        data.sigBytes = i + 1;
		    }
		};
	
	
		return CryptoJS.pad.ZeroPadding;
	
	}));

/***/ }),
/* 128 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(118));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./cipher-core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		/**
		 * A noop padding strategy.
		 */
		CryptoJS.pad.NoPadding = {
		    pad: function () {
		    },
	
		    unpad: function () {
		    }
		};
	
	
		return CryptoJS.pad.NoPadding;
	
	}));

/***/ }),
/* 129 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(118));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./cipher-core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		(function (undefined) {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var CipherParams = C_lib.CipherParams;
		    var C_enc = C.enc;
		    var Hex = C_enc.Hex;
		    var C_format = C.format;
	
		    var HexFormatter = C_format.Hex = {
		        /**
		         * Converts the ciphertext of a cipher params object to a hexadecimally encoded string.
		         *
		         * @param {CipherParams} cipherParams The cipher params object.
		         *
		         * @return {string} The hexadecimally encoded string.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var hexString = CryptoJS.format.Hex.stringify(cipherParams);
		         */
		        stringify: function (cipherParams) {
		            return cipherParams.ciphertext.toString(Hex);
		        },
	
		        /**
		         * Converts a hexadecimally encoded ciphertext string to a cipher params object.
		         *
		         * @param {string} input The hexadecimally encoded string.
		         *
		         * @return {CipherParams} The cipher params object.
		         *
		         * @static
		         *
		         * @example
		         *
		         *     var cipherParams = CryptoJS.format.Hex.parse(hexString);
		         */
		        parse: function (input) {
		            var ciphertext = Hex.parse(input);
		            return CipherParams.create({ ciphertext: ciphertext });
		        }
		    };
		}());
	
	
		return CryptoJS.format.Hex;
	
	}));

/***/ }),
/* 130 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(106), __webpack_require__(107), __webpack_require__(117), __webpack_require__(118));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./enc-base64", "./md5", "./evpkdf", "./cipher-core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		(function () {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var BlockCipher = C_lib.BlockCipher;
		    var C_algo = C.algo;
	
		    // Lookup tables
		    var SBOX = [];
		    var INV_SBOX = [];
		    var SUB_MIX_0 = [];
		    var SUB_MIX_1 = [];
		    var SUB_MIX_2 = [];
		    var SUB_MIX_3 = [];
		    var INV_SUB_MIX_0 = [];
		    var INV_SUB_MIX_1 = [];
		    var INV_SUB_MIX_2 = [];
		    var INV_SUB_MIX_3 = [];
	
		    // Compute lookup tables
		    (function () {
		        // Compute double table
		        var d = [];
		        for (var i = 0; i < 256; i++) {
		            if (i < 128) {
		                d[i] = i << 1;
		            } else {
		                d[i] = (i << 1) ^ 0x11b;
		            }
		        }
	
		        // Walk GF(2^8)
		        var x = 0;
		        var xi = 0;
		        for (var i = 0; i < 256; i++) {
		            // Compute sbox
		            var sx = xi ^ (xi << 1) ^ (xi << 2) ^ (xi << 3) ^ (xi << 4);
		            sx = (sx >>> 8) ^ (sx & 0xff) ^ 0x63;
		            SBOX[x] = sx;
		            INV_SBOX[sx] = x;
	
		            // Compute multiplication
		            var x2 = d[x];
		            var x4 = d[x2];
		            var x8 = d[x4];
	
		            // Compute sub bytes, mix columns tables
		            var t = (d[sx] * 0x101) ^ (sx * 0x1010100);
		            SUB_MIX_0[x] = (t << 24) | (t >>> 8);
		            SUB_MIX_1[x] = (t << 16) | (t >>> 16);
		            SUB_MIX_2[x] = (t << 8)  | (t >>> 24);
		            SUB_MIX_3[x] = t;
	
		            // Compute inv sub bytes, inv mix columns tables
		            var t = (x8 * 0x1010101) ^ (x4 * 0x10001) ^ (x2 * 0x101) ^ (x * 0x1010100);
		            INV_SUB_MIX_0[sx] = (t << 24) | (t >>> 8);
		            INV_SUB_MIX_1[sx] = (t << 16) | (t >>> 16);
		            INV_SUB_MIX_2[sx] = (t << 8)  | (t >>> 24);
		            INV_SUB_MIX_3[sx] = t;
	
		            // Compute next counter
		            if (!x) {
		                x = xi = 1;
		            } else {
		                x = x2 ^ d[d[d[x8 ^ x2]]];
		                xi ^= d[d[xi]];
		            }
		        }
		    }());
	
		    // Precomputed Rcon lookup
		    var RCON = [0x00, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36];
	
		    /**
		     * AES block cipher algorithm.
		     */
		    var AES = C_algo.AES = BlockCipher.extend({
		        _doReset: function () {
		            // Skip reset of nRounds has been set before and key did not change
		            if (this._nRounds && this._keyPriorReset === this._key) {
		                return;
		            }
	
		            // Shortcuts
		            var key = this._keyPriorReset = this._key;
		            var keyWords = key.words;
		            var keySize = key.sigBytes / 4;
	
		            // Compute number of rounds
		            var nRounds = this._nRounds = keySize + 6;
	
		            // Compute number of key schedule rows
		            var ksRows = (nRounds + 1) * 4;
	
		            // Compute key schedule
		            var keySchedule = this._keySchedule = [];
		            for (var ksRow = 0; ksRow < ksRows; ksRow++) {
		                if (ksRow < keySize) {
		                    keySchedule[ksRow] = keyWords[ksRow];
		                } else {
		                    var t = keySchedule[ksRow - 1];
	
		                    if (!(ksRow % keySize)) {
		                        // Rot word
		                        t = (t << 8) | (t >>> 24);
	
		                        // Sub word
		                        t = (SBOX[t >>> 24] << 24) | (SBOX[(t >>> 16) & 0xff] << 16) | (SBOX[(t >>> 8) & 0xff] << 8) | SBOX[t & 0xff];
	
		                        // Mix Rcon
		                        t ^= RCON[(ksRow / keySize) | 0] << 24;
		                    } else if (keySize > 6 && ksRow % keySize == 4) {
		                        // Sub word
		                        t = (SBOX[t >>> 24] << 24) | (SBOX[(t >>> 16) & 0xff] << 16) | (SBOX[(t >>> 8) & 0xff] << 8) | SBOX[t & 0xff];
		                    }
	
		                    keySchedule[ksRow] = keySchedule[ksRow - keySize] ^ t;
		                }
		            }
	
		            // Compute inv key schedule
		            var invKeySchedule = this._invKeySchedule = [];
		            for (var invKsRow = 0; invKsRow < ksRows; invKsRow++) {
		                var ksRow = ksRows - invKsRow;
	
		                if (invKsRow % 4) {
		                    var t = keySchedule[ksRow];
		                } else {
		                    var t = keySchedule[ksRow - 4];
		                }
	
		                if (invKsRow < 4 || ksRow <= 4) {
		                    invKeySchedule[invKsRow] = t;
		                } else {
		                    invKeySchedule[invKsRow] = INV_SUB_MIX_0[SBOX[t >>> 24]] ^ INV_SUB_MIX_1[SBOX[(t >>> 16) & 0xff]] ^
		                                               INV_SUB_MIX_2[SBOX[(t >>> 8) & 0xff]] ^ INV_SUB_MIX_3[SBOX[t & 0xff]];
		                }
		            }
		        },
	
		        encryptBlock: function (M, offset) {
		            this._doCryptBlock(M, offset, this._keySchedule, SUB_MIX_0, SUB_MIX_1, SUB_MIX_2, SUB_MIX_3, SBOX);
		        },
	
		        decryptBlock: function (M, offset) {
		            // Swap 2nd and 4th rows
		            var t = M[offset + 1];
		            M[offset + 1] = M[offset + 3];
		            M[offset + 3] = t;
	
		            this._doCryptBlock(M, offset, this._invKeySchedule, INV_SUB_MIX_0, INV_SUB_MIX_1, INV_SUB_MIX_2, INV_SUB_MIX_3, INV_SBOX);
	
		            // Inv swap 2nd and 4th rows
		            var t = M[offset + 1];
		            M[offset + 1] = M[offset + 3];
		            M[offset + 3] = t;
		        },
	
		        _doCryptBlock: function (M, offset, keySchedule, SUB_MIX_0, SUB_MIX_1, SUB_MIX_2, SUB_MIX_3, SBOX) {
		            // Shortcut
		            var nRounds = this._nRounds;
	
		            // Get input, add round key
		            var s0 = M[offset]     ^ keySchedule[0];
		            var s1 = M[offset + 1] ^ keySchedule[1];
		            var s2 = M[offset + 2] ^ keySchedule[2];
		            var s3 = M[offset + 3] ^ keySchedule[3];
	
		            // Key schedule row counter
		            var ksRow = 4;
	
		            // Rounds
		            for (var round = 1; round < nRounds; round++) {
		                // Shift rows, sub bytes, mix columns, add round key
		                var t0 = SUB_MIX_0[s0 >>> 24] ^ SUB_MIX_1[(s1 >>> 16) & 0xff] ^ SUB_MIX_2[(s2 >>> 8) & 0xff] ^ SUB_MIX_3[s3 & 0xff] ^ keySchedule[ksRow++];
		                var t1 = SUB_MIX_0[s1 >>> 24] ^ SUB_MIX_1[(s2 >>> 16) & 0xff] ^ SUB_MIX_2[(s3 >>> 8) & 0xff] ^ SUB_MIX_3[s0 & 0xff] ^ keySchedule[ksRow++];
		                var t2 = SUB_MIX_0[s2 >>> 24] ^ SUB_MIX_1[(s3 >>> 16) & 0xff] ^ SUB_MIX_2[(s0 >>> 8) & 0xff] ^ SUB_MIX_3[s1 & 0xff] ^ keySchedule[ksRow++];
		                var t3 = SUB_MIX_0[s3 >>> 24] ^ SUB_MIX_1[(s0 >>> 16) & 0xff] ^ SUB_MIX_2[(s1 >>> 8) & 0xff] ^ SUB_MIX_3[s2 & 0xff] ^ keySchedule[ksRow++];
	
		                // Update state
		                s0 = t0;
		                s1 = t1;
		                s2 = t2;
		                s3 = t3;
		            }
	
		            // Shift rows, sub bytes, add round key
		            var t0 = ((SBOX[s0 >>> 24] << 24) | (SBOX[(s1 >>> 16) & 0xff] << 16) | (SBOX[(s2 >>> 8) & 0xff] << 8) | SBOX[s3 & 0xff]) ^ keySchedule[ksRow++];
		            var t1 = ((SBOX[s1 >>> 24] << 24) | (SBOX[(s2 >>> 16) & 0xff] << 16) | (SBOX[(s3 >>> 8) & 0xff] << 8) | SBOX[s0 & 0xff]) ^ keySchedule[ksRow++];
		            var t2 = ((SBOX[s2 >>> 24] << 24) | (SBOX[(s3 >>> 16) & 0xff] << 16) | (SBOX[(s0 >>> 8) & 0xff] << 8) | SBOX[s1 & 0xff]) ^ keySchedule[ksRow++];
		            var t3 = ((SBOX[s3 >>> 24] << 24) | (SBOX[(s0 >>> 16) & 0xff] << 16) | (SBOX[(s1 >>> 8) & 0xff] << 8) | SBOX[s2 & 0xff]) ^ keySchedule[ksRow++];
	
		            // Set output
		            M[offset]     = t0;
		            M[offset + 1] = t1;
		            M[offset + 2] = t2;
		            M[offset + 3] = t3;
		        },
	
		        keySize: 256/32
		    });
	
		    /**
		     * Shortcut functions to the cipher's object interface.
		     *
		     * @example
		     *
		     *     var ciphertext = CryptoJS.AES.encrypt(message, key, cfg);
		     *     var plaintext  = CryptoJS.AES.decrypt(ciphertext, key, cfg);
		     */
		    C.AES = BlockCipher._createHelper(AES);
		}());
	
	
		return CryptoJS.AES;
	
	}));

/***/ }),
/* 131 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(106), __webpack_require__(107), __webpack_require__(117), __webpack_require__(118));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./enc-base64", "./md5", "./evpkdf", "./cipher-core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		(function () {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var WordArray = C_lib.WordArray;
		    var BlockCipher = C_lib.BlockCipher;
		    var C_algo = C.algo;
	
		    // Permuted Choice 1 constants
		    var PC1 = [
		        57, 49, 41, 33, 25, 17, 9,  1,
		        58, 50, 42, 34, 26, 18, 10, 2,
		        59, 51, 43, 35, 27, 19, 11, 3,
		        60, 52, 44, 36, 63, 55, 47, 39,
		        31, 23, 15, 7,  62, 54, 46, 38,
		        30, 22, 14, 6,  61, 53, 45, 37,
		        29, 21, 13, 5,  28, 20, 12, 4
		    ];
	
		    // Permuted Choice 2 constants
		    var PC2 = [
		        14, 17, 11, 24, 1,  5,
		        3,  28, 15, 6,  21, 10,
		        23, 19, 12, 4,  26, 8,
		        16, 7,  27, 20, 13, 2,
		        41, 52, 31, 37, 47, 55,
		        30, 40, 51, 45, 33, 48,
		        44, 49, 39, 56, 34, 53,
		        46, 42, 50, 36, 29, 32
		    ];
	
		    // Cumulative bit shift constants
		    var BIT_SHIFTS = [1,  2,  4,  6,  8,  10, 12, 14, 15, 17, 19, 21, 23, 25, 27, 28];
	
		    // SBOXes and round permutation constants
		    var SBOX_P = [
		        {
		            0x0: 0x808200,
		            0x10000000: 0x8000,
		            0x20000000: 0x808002,
		            0x30000000: 0x2,
		            0x40000000: 0x200,
		            0x50000000: 0x808202,
		            0x60000000: 0x800202,
		            0x70000000: 0x800000,
		            0x80000000: 0x202,
		            0x90000000: 0x800200,
		            0xa0000000: 0x8200,
		            0xb0000000: 0x808000,
		            0xc0000000: 0x8002,
		            0xd0000000: 0x800002,
		            0xe0000000: 0x0,
		            0xf0000000: 0x8202,
		            0x8000000: 0x0,
		            0x18000000: 0x808202,
		            0x28000000: 0x8202,
		            0x38000000: 0x8000,
		            0x48000000: 0x808200,
		            0x58000000: 0x200,
		            0x68000000: 0x808002,
		            0x78000000: 0x2,
		            0x88000000: 0x800200,
		            0x98000000: 0x8200,
		            0xa8000000: 0x808000,
		            0xb8000000: 0x800202,
		            0xc8000000: 0x800002,
		            0xd8000000: 0x8002,
		            0xe8000000: 0x202,
		            0xf8000000: 0x800000,
		            0x1: 0x8000,
		            0x10000001: 0x2,
		            0x20000001: 0x808200,
		            0x30000001: 0x800000,
		            0x40000001: 0x808002,
		            0x50000001: 0x8200,
		            0x60000001: 0x200,
		            0x70000001: 0x800202,
		            0x80000001: 0x808202,
		            0x90000001: 0x808000,
		            0xa0000001: 0x800002,
		            0xb0000001: 0x8202,
		            0xc0000001: 0x202,
		            0xd0000001: 0x800200,
		            0xe0000001: 0x8002,
		            0xf0000001: 0x0,
		            0x8000001: 0x808202,
		            0x18000001: 0x808000,
		            0x28000001: 0x800000,
		            0x38000001: 0x200,
		            0x48000001: 0x8000,
		            0x58000001: 0x800002,
		            0x68000001: 0x2,
		            0x78000001: 0x8202,
		            0x88000001: 0x8002,
		            0x98000001: 0x800202,
		            0xa8000001: 0x202,
		            0xb8000001: 0x808200,
		            0xc8000001: 0x800200,
		            0xd8000001: 0x0,
		            0xe8000001: 0x8200,
		            0xf8000001: 0x808002
		        },
		        {
		            0x0: 0x40084010,
		            0x1000000: 0x4000,
		            0x2000000: 0x80000,
		            0x3000000: 0x40080010,
		            0x4000000: 0x40000010,
		            0x5000000: 0x40084000,
		            0x6000000: 0x40004000,
		            0x7000000: 0x10,
		            0x8000000: 0x84000,
		            0x9000000: 0x40004010,
		            0xa000000: 0x40000000,
		            0xb000000: 0x84010,
		            0xc000000: 0x80010,
		            0xd000000: 0x0,
		            0xe000000: 0x4010,
		            0xf000000: 0x40080000,
		            0x800000: 0x40004000,
		            0x1800000: 0x84010,
		            0x2800000: 0x10,
		            0x3800000: 0x40004010,
		            0x4800000: 0x40084010,
		            0x5800000: 0x40000000,
		            0x6800000: 0x80000,
		            0x7800000: 0x40080010,
		            0x8800000: 0x80010,
		            0x9800000: 0x0,
		            0xa800000: 0x4000,
		            0xb800000: 0x40080000,
		            0xc800000: 0x40000010,
		            0xd800000: 0x84000,
		            0xe800000: 0x40084000,
		            0xf800000: 0x4010,
		            0x10000000: 0x0,
		            0x11000000: 0x40080010,
		            0x12000000: 0x40004010,
		            0x13000000: 0x40084000,
		            0x14000000: 0x40080000,
		            0x15000000: 0x10,
		            0x16000000: 0x84010,
		            0x17000000: 0x4000,
		            0x18000000: 0x4010,
		            0x19000000: 0x80000,
		            0x1a000000: 0x80010,
		            0x1b000000: 0x40000010,
		            0x1c000000: 0x84000,
		            0x1d000000: 0x40004000,
		            0x1e000000: 0x40000000,
		            0x1f000000: 0x40084010,
		            0x10800000: 0x84010,
		            0x11800000: 0x80000,
		            0x12800000: 0x40080000,
		            0x13800000: 0x4000,
		            0x14800000: 0x40004000,
		            0x15800000: 0x40084010,
		            0x16800000: 0x10,
		            0x17800000: 0x40000000,
		            0x18800000: 0x40084000,
		            0x19800000: 0x40000010,
		            0x1a800000: 0x40004010,
		            0x1b800000: 0x80010,
		            0x1c800000: 0x0,
		            0x1d800000: 0x4010,
		            0x1e800000: 0x40080010,
		            0x1f800000: 0x84000
		        },
		        {
		            0x0: 0x104,
		            0x100000: 0x0,
		            0x200000: 0x4000100,
		            0x300000: 0x10104,
		            0x400000: 0x10004,
		            0x500000: 0x4000004,
		            0x600000: 0x4010104,
		            0x700000: 0x4010000,
		            0x800000: 0x4000000,
		            0x900000: 0x4010100,
		            0xa00000: 0x10100,
		            0xb00000: 0x4010004,
		            0xc00000: 0x4000104,
		            0xd00000: 0x10000,
		            0xe00000: 0x4,
		            0xf00000: 0x100,
		            0x80000: 0x4010100,
		            0x180000: 0x4010004,
		            0x280000: 0x0,
		            0x380000: 0x4000100,
		            0x480000: 0x4000004,
		            0x580000: 0x10000,
		            0x680000: 0x10004,
		            0x780000: 0x104,
		            0x880000: 0x4,
		            0x980000: 0x100,
		            0xa80000: 0x4010000,
		            0xb80000: 0x10104,
		            0xc80000: 0x10100,
		            0xd80000: 0x4000104,
		            0xe80000: 0x4010104,
		            0xf80000: 0x4000000,
		            0x1000000: 0x4010100,
		            0x1100000: 0x10004,
		            0x1200000: 0x10000,
		            0x1300000: 0x4000100,
		            0x1400000: 0x100,
		            0x1500000: 0x4010104,
		            0x1600000: 0x4000004,
		            0x1700000: 0x0,
		            0x1800000: 0x4000104,
		            0x1900000: 0x4000000,
		            0x1a00000: 0x4,
		            0x1b00000: 0x10100,
		            0x1c00000: 0x4010000,
		            0x1d00000: 0x104,
		            0x1e00000: 0x10104,
		            0x1f00000: 0x4010004,
		            0x1080000: 0x4000000,
		            0x1180000: 0x104,
		            0x1280000: 0x4010100,
		            0x1380000: 0x0,
		            0x1480000: 0x10004,
		            0x1580000: 0x4000100,
		            0x1680000: 0x100,
		            0x1780000: 0x4010004,
		            0x1880000: 0x10000,
		            0x1980000: 0x4010104,
		            0x1a80000: 0x10104,
		            0x1b80000: 0x4000004,
		            0x1c80000: 0x4000104,
		            0x1d80000: 0x4010000,
		            0x1e80000: 0x4,
		            0x1f80000: 0x10100
		        },
		        {
		            0x0: 0x80401000,
		            0x10000: 0x80001040,
		            0x20000: 0x401040,
		            0x30000: 0x80400000,
		            0x40000: 0x0,
		            0x50000: 0x401000,
		            0x60000: 0x80000040,
		            0x70000: 0x400040,
		            0x80000: 0x80000000,
		            0x90000: 0x400000,
		            0xa0000: 0x40,
		            0xb0000: 0x80001000,
		            0xc0000: 0x80400040,
		            0xd0000: 0x1040,
		            0xe0000: 0x1000,
		            0xf0000: 0x80401040,
		            0x8000: 0x80001040,
		            0x18000: 0x40,
		            0x28000: 0x80400040,
		            0x38000: 0x80001000,
		            0x48000: 0x401000,
		            0x58000: 0x80401040,
		            0x68000: 0x0,
		            0x78000: 0x80400000,
		            0x88000: 0x1000,
		            0x98000: 0x80401000,
		            0xa8000: 0x400000,
		            0xb8000: 0x1040,
		            0xc8000: 0x80000000,
		            0xd8000: 0x400040,
		            0xe8000: 0x401040,
		            0xf8000: 0x80000040,
		            0x100000: 0x400040,
		            0x110000: 0x401000,
		            0x120000: 0x80000040,
		            0x130000: 0x0,
		            0x140000: 0x1040,
		            0x150000: 0x80400040,
		            0x160000: 0x80401000,
		            0x170000: 0x80001040,
		            0x180000: 0x80401040,
		            0x190000: 0x80000000,
		            0x1a0000: 0x80400000,
		            0x1b0000: 0x401040,
		            0x1c0000: 0x80001000,
		            0x1d0000: 0x400000,
		            0x1e0000: 0x40,
		            0x1f0000: 0x1000,
		            0x108000: 0x80400000,
		            0x118000: 0x80401040,
		            0x128000: 0x0,
		            0x138000: 0x401000,
		            0x148000: 0x400040,
		            0x158000: 0x80000000,
		            0x168000: 0x80001040,
		            0x178000: 0x40,
		            0x188000: 0x80000040,
		            0x198000: 0x1000,
		            0x1a8000: 0x80001000,
		            0x1b8000: 0x80400040,
		            0x1c8000: 0x1040,
		            0x1d8000: 0x80401000,
		            0x1e8000: 0x400000,
		            0x1f8000: 0x401040
		        },
		        {
		            0x0: 0x80,
		            0x1000: 0x1040000,
		            0x2000: 0x40000,
		            0x3000: 0x20000000,
		            0x4000: 0x20040080,
		            0x5000: 0x1000080,
		            0x6000: 0x21000080,
		            0x7000: 0x40080,
		            0x8000: 0x1000000,
		            0x9000: 0x20040000,
		            0xa000: 0x20000080,
		            0xb000: 0x21040080,
		            0xc000: 0x21040000,
		            0xd000: 0x0,
		            0xe000: 0x1040080,
		            0xf000: 0x21000000,
		            0x800: 0x1040080,
		            0x1800: 0x21000080,
		            0x2800: 0x80,
		            0x3800: 0x1040000,
		            0x4800: 0x40000,
		            0x5800: 0x20040080,
		            0x6800: 0x21040000,
		            0x7800: 0x20000000,
		            0x8800: 0x20040000,
		            0x9800: 0x0,
		            0xa800: 0x21040080,
		            0xb800: 0x1000080,
		            0xc800: 0x20000080,
		            0xd800: 0x21000000,
		            0xe800: 0x1000000,
		            0xf800: 0x40080,
		            0x10000: 0x40000,
		            0x11000: 0x80,
		            0x12000: 0x20000000,
		            0x13000: 0x21000080,
		            0x14000: 0x1000080,
		            0x15000: 0x21040000,
		            0x16000: 0x20040080,
		            0x17000: 0x1000000,
		            0x18000: 0x21040080,
		            0x19000: 0x21000000,
		            0x1a000: 0x1040000,
		            0x1b000: 0x20040000,
		            0x1c000: 0x40080,
		            0x1d000: 0x20000080,
		            0x1e000: 0x0,
		            0x1f000: 0x1040080,
		            0x10800: 0x21000080,
		            0x11800: 0x1000000,
		            0x12800: 0x1040000,
		            0x13800: 0x20040080,
		            0x14800: 0x20000000,
		            0x15800: 0x1040080,
		            0x16800: 0x80,
		            0x17800: 0x21040000,
		            0x18800: 0x40080,
		            0x19800: 0x21040080,
		            0x1a800: 0x0,
		            0x1b800: 0x21000000,
		            0x1c800: 0x1000080,
		            0x1d800: 0x40000,
		            0x1e800: 0x20040000,
		            0x1f800: 0x20000080
		        },
		        {
		            0x0: 0x10000008,
		            0x100: 0x2000,
		            0x200: 0x10200000,
		            0x300: 0x10202008,
		            0x400: 0x10002000,
		            0x500: 0x200000,
		            0x600: 0x200008,
		            0x700: 0x10000000,
		            0x800: 0x0,
		            0x900: 0x10002008,
		            0xa00: 0x202000,
		            0xb00: 0x8,
		            0xc00: 0x10200008,
		            0xd00: 0x202008,
		            0xe00: 0x2008,
		            0xf00: 0x10202000,
		            0x80: 0x10200000,
		            0x180: 0x10202008,
		            0x280: 0x8,
		            0x380: 0x200000,
		            0x480: 0x202008,
		            0x580: 0x10000008,
		            0x680: 0x10002000,
		            0x780: 0x2008,
		            0x880: 0x200008,
		            0x980: 0x2000,
		            0xa80: 0x10002008,
		            0xb80: 0x10200008,
		            0xc80: 0x0,
		            0xd80: 0x10202000,
		            0xe80: 0x202000,
		            0xf80: 0x10000000,
		            0x1000: 0x10002000,
		            0x1100: 0x10200008,
		            0x1200: 0x10202008,
		            0x1300: 0x2008,
		            0x1400: 0x200000,
		            0x1500: 0x10000000,
		            0x1600: 0x10000008,
		            0x1700: 0x202000,
		            0x1800: 0x202008,
		            0x1900: 0x0,
		            0x1a00: 0x8,
		            0x1b00: 0x10200000,
		            0x1c00: 0x2000,
		            0x1d00: 0x10002008,
		            0x1e00: 0x10202000,
		            0x1f00: 0x200008,
		            0x1080: 0x8,
		            0x1180: 0x202000,
		            0x1280: 0x200000,
		            0x1380: 0x10000008,
		            0x1480: 0x10002000,
		            0x1580: 0x2008,
		            0x1680: 0x10202008,
		            0x1780: 0x10200000,
		            0x1880: 0x10202000,
		            0x1980: 0x10200008,
		            0x1a80: 0x2000,
		            0x1b80: 0x202008,
		            0x1c80: 0x200008,
		            0x1d80: 0x0,
		            0x1e80: 0x10000000,
		            0x1f80: 0x10002008
		        },
		        {
		            0x0: 0x100000,
		            0x10: 0x2000401,
		            0x20: 0x400,
		            0x30: 0x100401,
		            0x40: 0x2100401,
		            0x50: 0x0,
		            0x60: 0x1,
		            0x70: 0x2100001,
		            0x80: 0x2000400,
		            0x90: 0x100001,
		            0xa0: 0x2000001,
		            0xb0: 0x2100400,
		            0xc0: 0x2100000,
		            0xd0: 0x401,
		            0xe0: 0x100400,
		            0xf0: 0x2000000,
		            0x8: 0x2100001,
		            0x18: 0x0,
		            0x28: 0x2000401,
		            0x38: 0x2100400,
		            0x48: 0x100000,
		            0x58: 0x2000001,
		            0x68: 0x2000000,
		            0x78: 0x401,
		            0x88: 0x100401,
		            0x98: 0x2000400,
		            0xa8: 0x2100000,
		            0xb8: 0x100001,
		            0xc8: 0x400,
		            0xd8: 0x2100401,
		            0xe8: 0x1,
		            0xf8: 0x100400,
		            0x100: 0x2000000,
		            0x110: 0x100000,
		            0x120: 0x2000401,
		            0x130: 0x2100001,
		            0x140: 0x100001,
		            0x150: 0x2000400,
		            0x160: 0x2100400,
		            0x170: 0x100401,
		            0x180: 0x401,
		            0x190: 0x2100401,
		            0x1a0: 0x100400,
		            0x1b0: 0x1,
		            0x1c0: 0x0,
		            0x1d0: 0x2100000,
		            0x1e0: 0x2000001,
		            0x1f0: 0x400,
		            0x108: 0x100400,
		            0x118: 0x2000401,
		            0x128: 0x2100001,
		            0x138: 0x1,
		            0x148: 0x2000000,
		            0x158: 0x100000,
		            0x168: 0x401,
		            0x178: 0x2100400,
		            0x188: 0x2000001,
		            0x198: 0x2100000,
		            0x1a8: 0x0,
		            0x1b8: 0x2100401,
		            0x1c8: 0x100401,
		            0x1d8: 0x400,
		            0x1e8: 0x2000400,
		            0x1f8: 0x100001
		        },
		        {
		            0x0: 0x8000820,
		            0x1: 0x20000,
		            0x2: 0x8000000,
		            0x3: 0x20,
		            0x4: 0x20020,
		            0x5: 0x8020820,
		            0x6: 0x8020800,
		            0x7: 0x800,
		            0x8: 0x8020000,
		            0x9: 0x8000800,
		            0xa: 0x20800,
		            0xb: 0x8020020,
		            0xc: 0x820,
		            0xd: 0x0,
		            0xe: 0x8000020,
		            0xf: 0x20820,
		            0x80000000: 0x800,
		            0x80000001: 0x8020820,
		            0x80000002: 0x8000820,
		            0x80000003: 0x8000000,
		            0x80000004: 0x8020000,
		            0x80000005: 0x20800,
		            0x80000006: 0x20820,
		            0x80000007: 0x20,
		            0x80000008: 0x8000020,
		            0x80000009: 0x820,
		            0x8000000a: 0x20020,
		            0x8000000b: 0x8020800,
		            0x8000000c: 0x0,
		            0x8000000d: 0x8020020,
		            0x8000000e: 0x8000800,
		            0x8000000f: 0x20000,
		            0x10: 0x20820,
		            0x11: 0x8020800,
		            0x12: 0x20,
		            0x13: 0x800,
		            0x14: 0x8000800,
		            0x15: 0x8000020,
		            0x16: 0x8020020,
		            0x17: 0x20000,
		            0x18: 0x0,
		            0x19: 0x20020,
		            0x1a: 0x8020000,
		            0x1b: 0x8000820,
		            0x1c: 0x8020820,
		            0x1d: 0x20800,
		            0x1e: 0x820,
		            0x1f: 0x8000000,
		            0x80000010: 0x20000,
		            0x80000011: 0x800,
		            0x80000012: 0x8020020,
		            0x80000013: 0x20820,
		            0x80000014: 0x20,
		            0x80000015: 0x8020000,
		            0x80000016: 0x8000000,
		            0x80000017: 0x8000820,
		            0x80000018: 0x8020820,
		            0x80000019: 0x8000020,
		            0x8000001a: 0x8000800,
		            0x8000001b: 0x0,
		            0x8000001c: 0x20800,
		            0x8000001d: 0x820,
		            0x8000001e: 0x20020,
		            0x8000001f: 0x8020800
		        }
		    ];
	
		    // Masks that select the SBOX input
		    var SBOX_MASK = [
		        0xf8000001, 0x1f800000, 0x01f80000, 0x001f8000,
		        0x0001f800, 0x00001f80, 0x000001f8, 0x8000001f
		    ];
	
		    /**
		     * DES block cipher algorithm.
		     */
		    var DES = C_algo.DES = BlockCipher.extend({
		        _doReset: function () {
		            // Shortcuts
		            var key = this._key;
		            var keyWords = key.words;
	
		            // Select 56 bits according to PC1
		            var keyBits = [];
		            for (var i = 0; i < 56; i++) {
		                var keyBitPos = PC1[i] - 1;
		                keyBits[i] = (keyWords[keyBitPos >>> 5] >>> (31 - keyBitPos % 32)) & 1;
		            }
	
		            // Assemble 16 subkeys
		            var subKeys = this._subKeys = [];
		            for (var nSubKey = 0; nSubKey < 16; nSubKey++) {
		                // Create subkey
		                var subKey = subKeys[nSubKey] = [];
	
		                // Shortcut
		                var bitShift = BIT_SHIFTS[nSubKey];
	
		                // Select 48 bits according to PC2
		                for (var i = 0; i < 24; i++) {
		                    // Select from the left 28 key bits
		                    subKey[(i / 6) | 0] |= keyBits[((PC2[i] - 1) + bitShift) % 28] << (31 - i % 6);
	
		                    // Select from the right 28 key bits
		                    subKey[4 + ((i / 6) | 0)] |= keyBits[28 + (((PC2[i + 24] - 1) + bitShift) % 28)] << (31 - i % 6);
		                }
	
		                // Since each subkey is applied to an expanded 32-bit input,
		                // the subkey can be broken into 8 values scaled to 32-bits,
		                // which allows the key to be used without expansion
		                subKey[0] = (subKey[0] << 1) | (subKey[0] >>> 31);
		                for (var i = 1; i < 7; i++) {
		                    subKey[i] = subKey[i] >>> ((i - 1) * 4 + 3);
		                }
		                subKey[7] = (subKey[7] << 5) | (subKey[7] >>> 27);
		            }
	
		            // Compute inverse subkeys
		            var invSubKeys = this._invSubKeys = [];
		            for (var i = 0; i < 16; i++) {
		                invSubKeys[i] = subKeys[15 - i];
		            }
		        },
	
		        encryptBlock: function (M, offset) {
		            this._doCryptBlock(M, offset, this._subKeys);
		        },
	
		        decryptBlock: function (M, offset) {
		            this._doCryptBlock(M, offset, this._invSubKeys);
		        },
	
		        _doCryptBlock: function (M, offset, subKeys) {
		            // Get input
		            this._lBlock = M[offset];
		            this._rBlock = M[offset + 1];
	
		            // Initial permutation
		            exchangeLR.call(this, 4,  0x0f0f0f0f);
		            exchangeLR.call(this, 16, 0x0000ffff);
		            exchangeRL.call(this, 2,  0x33333333);
		            exchangeRL.call(this, 8,  0x00ff00ff);
		            exchangeLR.call(this, 1,  0x55555555);
	
		            // Rounds
		            for (var round = 0; round < 16; round++) {
		                // Shortcuts
		                var subKey = subKeys[round];
		                var lBlock = this._lBlock;
		                var rBlock = this._rBlock;
	
		                // Feistel function
		                var f = 0;
		                for (var i = 0; i < 8; i++) {
		                    f |= SBOX_P[i][((rBlock ^ subKey[i]) & SBOX_MASK[i]) >>> 0];
		                }
		                this._lBlock = rBlock;
		                this._rBlock = lBlock ^ f;
		            }
	
		            // Undo swap from last round
		            var t = this._lBlock;
		            this._lBlock = this._rBlock;
		            this._rBlock = t;
	
		            // Final permutation
		            exchangeLR.call(this, 1,  0x55555555);
		            exchangeRL.call(this, 8,  0x00ff00ff);
		            exchangeRL.call(this, 2,  0x33333333);
		            exchangeLR.call(this, 16, 0x0000ffff);
		            exchangeLR.call(this, 4,  0x0f0f0f0f);
	
		            // Set output
		            M[offset] = this._lBlock;
		            M[offset + 1] = this._rBlock;
		        },
	
		        keySize: 64/32,
	
		        ivSize: 64/32,
	
		        blockSize: 64/32
		    });
	
		    // Swap bits across the left and right words
		    function exchangeLR(offset, mask) {
		        var t = ((this._lBlock >>> offset) ^ this._rBlock) & mask;
		        this._rBlock ^= t;
		        this._lBlock ^= t << offset;
		    }
	
		    function exchangeRL(offset, mask) {
		        var t = ((this._rBlock >>> offset) ^ this._lBlock) & mask;
		        this._lBlock ^= t;
		        this._rBlock ^= t << offset;
		    }
	
		    /**
		     * Shortcut functions to the cipher's object interface.
		     *
		     * @example
		     *
		     *     var ciphertext = CryptoJS.DES.encrypt(message, key, cfg);
		     *     var plaintext  = CryptoJS.DES.decrypt(ciphertext, key, cfg);
		     */
		    C.DES = BlockCipher._createHelper(DES);
	
		    /**
		     * Triple-DES block cipher algorithm.
		     */
		    var TripleDES = C_algo.TripleDES = BlockCipher.extend({
		        _doReset: function () {
		            // Shortcuts
		            var key = this._key;
		            var keyWords = key.words;
	
		            // Create DES instances
		            this._des1 = DES.createEncryptor(WordArray.create(keyWords.slice(0, 2)));
		            this._des2 = DES.createEncryptor(WordArray.create(keyWords.slice(2, 4)));
		            this._des3 = DES.createEncryptor(WordArray.create(keyWords.slice(4, 6)));
		        },
	
		        encryptBlock: function (M, offset) {
		            this._des1.encryptBlock(M, offset);
		            this._des2.decryptBlock(M, offset);
		            this._des3.encryptBlock(M, offset);
		        },
	
		        decryptBlock: function (M, offset) {
		            this._des3.decryptBlock(M, offset);
		            this._des2.encryptBlock(M, offset);
		            this._des1.decryptBlock(M, offset);
		        },
	
		        keySize: 192/32,
	
		        ivSize: 64/32,
	
		        blockSize: 64/32
		    });
	
		    /**
		     * Shortcut functions to the cipher's object interface.
		     *
		     * @example
		     *
		     *     var ciphertext = CryptoJS.TripleDES.encrypt(message, key, cfg);
		     *     var plaintext  = CryptoJS.TripleDES.decrypt(ciphertext, key, cfg);
		     */
		    C.TripleDES = BlockCipher._createHelper(TripleDES);
		}());
	
	
		return CryptoJS.TripleDES;
	
	}));

/***/ }),
/* 132 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(106), __webpack_require__(107), __webpack_require__(117), __webpack_require__(118));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./enc-base64", "./md5", "./evpkdf", "./cipher-core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		(function () {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var StreamCipher = C_lib.StreamCipher;
		    var C_algo = C.algo;
	
		    /**
		     * RC4 stream cipher algorithm.
		     */
		    var RC4 = C_algo.RC4 = StreamCipher.extend({
		        _doReset: function () {
		            // Shortcuts
		            var key = this._key;
		            var keyWords = key.words;
		            var keySigBytes = key.sigBytes;
	
		            // Init sbox
		            var S = this._S = [];
		            for (var i = 0; i < 256; i++) {
		                S[i] = i;
		            }
	
		            // Key setup
		            for (var i = 0, j = 0; i < 256; i++) {
		                var keyByteIndex = i % keySigBytes;
		                var keyByte = (keyWords[keyByteIndex >>> 2] >>> (24 - (keyByteIndex % 4) * 8)) & 0xff;
	
		                j = (j + S[i] + keyByte) % 256;
	
		                // Swap
		                var t = S[i];
		                S[i] = S[j];
		                S[j] = t;
		            }
	
		            // Counters
		            this._i = this._j = 0;
		        },
	
		        _doProcessBlock: function (M, offset) {
		            M[offset] ^= generateKeystreamWord.call(this);
		        },
	
		        keySize: 256/32,
	
		        ivSize: 0
		    });
	
		    function generateKeystreamWord() {
		        // Shortcuts
		        var S = this._S;
		        var i = this._i;
		        var j = this._j;
	
		        // Generate keystream word
		        var keystreamWord = 0;
		        for (var n = 0; n < 4; n++) {
		            i = (i + 1) % 256;
		            j = (j + S[i]) % 256;
	
		            // Swap
		            var t = S[i];
		            S[i] = S[j];
		            S[j] = t;
	
		            keystreamWord |= S[(S[i] + S[j]) % 256] << (24 - n * 8);
		        }
	
		        // Update counters
		        this._i = i;
		        this._j = j;
	
		        return keystreamWord;
		    }
	
		    /**
		     * Shortcut functions to the cipher's object interface.
		     *
		     * @example
		     *
		     *     var ciphertext = CryptoJS.RC4.encrypt(message, key, cfg);
		     *     var plaintext  = CryptoJS.RC4.decrypt(ciphertext, key, cfg);
		     */
		    C.RC4 = StreamCipher._createHelper(RC4);
	
		    /**
		     * Modified RC4 stream cipher algorithm.
		     */
		    var RC4Drop = C_algo.RC4Drop = RC4.extend({
		        /**
		         * Configuration options.
		         *
		         * @property {number} drop The number of keystream words to drop. Default 192
		         */
		        cfg: RC4.cfg.extend({
		            drop: 192
		        }),
	
		        _doReset: function () {
		            RC4._doReset.call(this);
	
		            // Drop
		            for (var i = this.cfg.drop; i > 0; i--) {
		                generateKeystreamWord.call(this);
		            }
		        }
		    });
	
		    /**
		     * Shortcut functions to the cipher's object interface.
		     *
		     * @example
		     *
		     *     var ciphertext = CryptoJS.RC4Drop.encrypt(message, key, cfg);
		     *     var plaintext  = CryptoJS.RC4Drop.decrypt(ciphertext, key, cfg);
		     */
		    C.RC4Drop = StreamCipher._createHelper(RC4Drop);
		}());
	
	
		return CryptoJS.RC4;
	
	}));

/***/ }),
/* 133 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(106), __webpack_require__(107), __webpack_require__(117), __webpack_require__(118));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./enc-base64", "./md5", "./evpkdf", "./cipher-core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		(function () {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var StreamCipher = C_lib.StreamCipher;
		    var C_algo = C.algo;
	
		    // Reusable objects
		    var S  = [];
		    var C_ = [];
		    var G  = [];
	
		    /**
		     * Rabbit stream cipher algorithm
		     */
		    var Rabbit = C_algo.Rabbit = StreamCipher.extend({
		        _doReset: function () {
		            // Shortcuts
		            var K = this._key.words;
		            var iv = this.cfg.iv;
	
		            // Swap endian
		            for (var i = 0; i < 4; i++) {
		                K[i] = (((K[i] << 8)  | (K[i] >>> 24)) & 0x00ff00ff) |
		                       (((K[i] << 24) | (K[i] >>> 8))  & 0xff00ff00);
		            }
	
		            // Generate initial state values
		            var X = this._X = [
		                K[0], (K[3] << 16) | (K[2] >>> 16),
		                K[1], (K[0] << 16) | (K[3] >>> 16),
		                K[2], (K[1] << 16) | (K[0] >>> 16),
		                K[3], (K[2] << 16) | (K[1] >>> 16)
		            ];
	
		            // Generate initial counter values
		            var C = this._C = [
		                (K[2] << 16) | (K[2] >>> 16), (K[0] & 0xffff0000) | (K[1] & 0x0000ffff),
		                (K[3] << 16) | (K[3] >>> 16), (K[1] & 0xffff0000) | (K[2] & 0x0000ffff),
		                (K[0] << 16) | (K[0] >>> 16), (K[2] & 0xffff0000) | (K[3] & 0x0000ffff),
		                (K[1] << 16) | (K[1] >>> 16), (K[3] & 0xffff0000) | (K[0] & 0x0000ffff)
		            ];
	
		            // Carry bit
		            this._b = 0;
	
		            // Iterate the system four times
		            for (var i = 0; i < 4; i++) {
		                nextState.call(this);
		            }
	
		            // Modify the counters
		            for (var i = 0; i < 8; i++) {
		                C[i] ^= X[(i + 4) & 7];
		            }
	
		            // IV setup
		            if (iv) {
		                // Shortcuts
		                var IV = iv.words;
		                var IV_0 = IV[0];
		                var IV_1 = IV[1];
	
		                // Generate four subvectors
		                var i0 = (((IV_0 << 8) | (IV_0 >>> 24)) & 0x00ff00ff) | (((IV_0 << 24) | (IV_0 >>> 8)) & 0xff00ff00);
		                var i2 = (((IV_1 << 8) | (IV_1 >>> 24)) & 0x00ff00ff) | (((IV_1 << 24) | (IV_1 >>> 8)) & 0xff00ff00);
		                var i1 = (i0 >>> 16) | (i2 & 0xffff0000);
		                var i3 = (i2 << 16)  | (i0 & 0x0000ffff);
	
		                // Modify counter values
		                C[0] ^= i0;
		                C[1] ^= i1;
		                C[2] ^= i2;
		                C[3] ^= i3;
		                C[4] ^= i0;
		                C[5] ^= i1;
		                C[6] ^= i2;
		                C[7] ^= i3;
	
		                // Iterate the system four times
		                for (var i = 0; i < 4; i++) {
		                    nextState.call(this);
		                }
		            }
		        },
	
		        _doProcessBlock: function (M, offset) {
		            // Shortcut
		            var X = this._X;
	
		            // Iterate the system
		            nextState.call(this);
	
		            // Generate four keystream words
		            S[0] = X[0] ^ (X[5] >>> 16) ^ (X[3] << 16);
		            S[1] = X[2] ^ (X[7] >>> 16) ^ (X[5] << 16);
		            S[2] = X[4] ^ (X[1] >>> 16) ^ (X[7] << 16);
		            S[3] = X[6] ^ (X[3] >>> 16) ^ (X[1] << 16);
	
		            for (var i = 0; i < 4; i++) {
		                // Swap endian
		                S[i] = (((S[i] << 8)  | (S[i] >>> 24)) & 0x00ff00ff) |
		                       (((S[i] << 24) | (S[i] >>> 8))  & 0xff00ff00);
	
		                // Encrypt
		                M[offset + i] ^= S[i];
		            }
		        },
	
		        blockSize: 128/32,
	
		        ivSize: 64/32
		    });
	
		    function nextState() {
		        // Shortcuts
		        var X = this._X;
		        var C = this._C;
	
		        // Save old counter values
		        for (var i = 0; i < 8; i++) {
		            C_[i] = C[i];
		        }
	
		        // Calculate new counter values
		        C[0] = (C[0] + 0x4d34d34d + this._b) | 0;
		        C[1] = (C[1] + 0xd34d34d3 + ((C[0] >>> 0) < (C_[0] >>> 0) ? 1 : 0)) | 0;
		        C[2] = (C[2] + 0x34d34d34 + ((C[1] >>> 0) < (C_[1] >>> 0) ? 1 : 0)) | 0;
		        C[3] = (C[3] + 0x4d34d34d + ((C[2] >>> 0) < (C_[2] >>> 0) ? 1 : 0)) | 0;
		        C[4] = (C[4] + 0xd34d34d3 + ((C[3] >>> 0) < (C_[3] >>> 0) ? 1 : 0)) | 0;
		        C[5] = (C[5] + 0x34d34d34 + ((C[4] >>> 0) < (C_[4] >>> 0) ? 1 : 0)) | 0;
		        C[6] = (C[6] + 0x4d34d34d + ((C[5] >>> 0) < (C_[5] >>> 0) ? 1 : 0)) | 0;
		        C[7] = (C[7] + 0xd34d34d3 + ((C[6] >>> 0) < (C_[6] >>> 0) ? 1 : 0)) | 0;
		        this._b = (C[7] >>> 0) < (C_[7] >>> 0) ? 1 : 0;
	
		        // Calculate the g-values
		        for (var i = 0; i < 8; i++) {
		            var gx = X[i] + C[i];
	
		            // Construct high and low argument for squaring
		            var ga = gx & 0xffff;
		            var gb = gx >>> 16;
	
		            // Calculate high and low result of squaring
		            var gh = ((((ga * ga) >>> 17) + ga * gb) >>> 15) + gb * gb;
		            var gl = (((gx & 0xffff0000) * gx) | 0) + (((gx & 0x0000ffff) * gx) | 0);
	
		            // High XOR low
		            G[i] = gh ^ gl;
		        }
	
		        // Calculate new state values
		        X[0] = (G[0] + ((G[7] << 16) | (G[7] >>> 16)) + ((G[6] << 16) | (G[6] >>> 16))) | 0;
		        X[1] = (G[1] + ((G[0] << 8)  | (G[0] >>> 24)) + G[7]) | 0;
		        X[2] = (G[2] + ((G[1] << 16) | (G[1] >>> 16)) + ((G[0] << 16) | (G[0] >>> 16))) | 0;
		        X[3] = (G[3] + ((G[2] << 8)  | (G[2] >>> 24)) + G[1]) | 0;
		        X[4] = (G[4] + ((G[3] << 16) | (G[3] >>> 16)) + ((G[2] << 16) | (G[2] >>> 16))) | 0;
		        X[5] = (G[5] + ((G[4] << 8)  | (G[4] >>> 24)) + G[3]) | 0;
		        X[6] = (G[6] + ((G[5] << 16) | (G[5] >>> 16)) + ((G[4] << 16) | (G[4] >>> 16))) | 0;
		        X[7] = (G[7] + ((G[6] << 8)  | (G[6] >>> 24)) + G[5]) | 0;
		    }
	
		    /**
		     * Shortcut functions to the cipher's object interface.
		     *
		     * @example
		     *
		     *     var ciphertext = CryptoJS.Rabbit.encrypt(message, key, cfg);
		     *     var plaintext  = CryptoJS.Rabbit.decrypt(ciphertext, key, cfg);
		     */
		    C.Rabbit = StreamCipher._createHelper(Rabbit);
		}());
	
	
		return CryptoJS.Rabbit;
	
	}));

/***/ }),
/* 134 */
/***/ (function(module, exports, __webpack_require__) {

	;(function (root, factory, undef) {
		if (true) {
			// CommonJS
			module.exports = exports = factory(__webpack_require__(102), __webpack_require__(106), __webpack_require__(107), __webpack_require__(117), __webpack_require__(118));
		}
		else if (typeof define === "function" && define.amd) {
			// AMD
			define(["./core", "./enc-base64", "./md5", "./evpkdf", "./cipher-core"], factory);
		}
		else {
			// Global (browser)
			factory(root.CryptoJS);
		}
	}(this, function (CryptoJS) {
	
		(function () {
		    // Shortcuts
		    var C = CryptoJS;
		    var C_lib = C.lib;
		    var StreamCipher = C_lib.StreamCipher;
		    var C_algo = C.algo;
	
		    // Reusable objects
		    var S  = [];
		    var C_ = [];
		    var G  = [];
	
		    /**
		     * Rabbit stream cipher algorithm.
		     *
		     * This is a legacy version that neglected to convert the key to little-endian.
		     * This error doesn't affect the cipher's security,
		     * but it does affect its compatibility with other implementations.
		     */
		    var RabbitLegacy = C_algo.RabbitLegacy = StreamCipher.extend({
		        _doReset: function () {
		            // Shortcuts
		            var K = this._key.words;
		            var iv = this.cfg.iv;
	
		            // Generate initial state values
		            var X = this._X = [
		                K[0], (K[3] << 16) | (K[2] >>> 16),
		                K[1], (K[0] << 16) | (K[3] >>> 16),
		                K[2], (K[1] << 16) | (K[0] >>> 16),
		                K[3], (K[2] << 16) | (K[1] >>> 16)
		            ];
	
		            // Generate initial counter values
		            var C = this._C = [
		                (K[2] << 16) | (K[2] >>> 16), (K[0] & 0xffff0000) | (K[1] & 0x0000ffff),
		                (K[3] << 16) | (K[3] >>> 16), (K[1] & 0xffff0000) | (K[2] & 0x0000ffff),
		                (K[0] << 16) | (K[0] >>> 16), (K[2] & 0xffff0000) | (K[3] & 0x0000ffff),
		                (K[1] << 16) | (K[1] >>> 16), (K[3] & 0xffff0000) | (K[0] & 0x0000ffff)
		            ];
	
		            // Carry bit
		            this._b = 0;
	
		            // Iterate the system four times
		            for (var i = 0; i < 4; i++) {
		                nextState.call(this);
		            }
	
		            // Modify the counters
		            for (var i = 0; i < 8; i++) {
		                C[i] ^= X[(i + 4) & 7];
		            }
	
		            // IV setup
		            if (iv) {
		                // Shortcuts
		                var IV = iv.words;
		                var IV_0 = IV[0];
		                var IV_1 = IV[1];
	
		                // Generate four subvectors
		                var i0 = (((IV_0 << 8) | (IV_0 >>> 24)) & 0x00ff00ff) | (((IV_0 << 24) | (IV_0 >>> 8)) & 0xff00ff00);
		                var i2 = (((IV_1 << 8) | (IV_1 >>> 24)) & 0x00ff00ff) | (((IV_1 << 24) | (IV_1 >>> 8)) & 0xff00ff00);
		                var i1 = (i0 >>> 16) | (i2 & 0xffff0000);
		                var i3 = (i2 << 16)  | (i0 & 0x0000ffff);
	
		                // Modify counter values
		                C[0] ^= i0;
		                C[1] ^= i1;
		                C[2] ^= i2;
		                C[3] ^= i3;
		                C[4] ^= i0;
		                C[5] ^= i1;
		                C[6] ^= i2;
		                C[7] ^= i3;
	
		                // Iterate the system four times
		                for (var i = 0; i < 4; i++) {
		                    nextState.call(this);
		                }
		            }
		        },
	
		        _doProcessBlock: function (M, offset) {
		            // Shortcut
		            var X = this._X;
	
		            // Iterate the system
		            nextState.call(this);
	
		            // Generate four keystream words
		            S[0] = X[0] ^ (X[5] >>> 16) ^ (X[3] << 16);
		            S[1] = X[2] ^ (X[7] >>> 16) ^ (X[5] << 16);
		            S[2] = X[4] ^ (X[1] >>> 16) ^ (X[7] << 16);
		            S[3] = X[6] ^ (X[3] >>> 16) ^ (X[1] << 16);
	
		            for (var i = 0; i < 4; i++) {
		                // Swap endian
		                S[i] = (((S[i] << 8)  | (S[i] >>> 24)) & 0x00ff00ff) |
		                       (((S[i] << 24) | (S[i] >>> 8))  & 0xff00ff00);
	
		                // Encrypt
		                M[offset + i] ^= S[i];
		            }
		        },
	
		        blockSize: 128/32,
	
		        ivSize: 64/32
		    });
	
		    function nextState() {
		        // Shortcuts
		        var X = this._X;
		        var C = this._C;
	
		        // Save old counter values
		        for (var i = 0; i < 8; i++) {
		            C_[i] = C[i];
		        }
	
		        // Calculate new counter values
		        C[0] = (C[0] + 0x4d34d34d + this._b) | 0;
		        C[1] = (C[1] + 0xd34d34d3 + ((C[0] >>> 0) < (C_[0] >>> 0) ? 1 : 0)) | 0;
		        C[2] = (C[2] + 0x34d34d34 + ((C[1] >>> 0) < (C_[1] >>> 0) ? 1 : 0)) | 0;
		        C[3] = (C[3] + 0x4d34d34d + ((C[2] >>> 0) < (C_[2] >>> 0) ? 1 : 0)) | 0;
		        C[4] = (C[4] + 0xd34d34d3 + ((C[3] >>> 0) < (C_[3] >>> 0) ? 1 : 0)) | 0;
		        C[5] = (C[5] + 0x34d34d34 + ((C[4] >>> 0) < (C_[4] >>> 0) ? 1 : 0)) | 0;
		        C[6] = (C[6] + 0x4d34d34d + ((C[5] >>> 0) < (C_[5] >>> 0) ? 1 : 0)) | 0;
		        C[7] = (C[7] + 0xd34d34d3 + ((C[6] >>> 0) < (C_[6] >>> 0) ? 1 : 0)) | 0;
		        this._b = (C[7] >>> 0) < (C_[7] >>> 0) ? 1 : 0;
	
		        // Calculate the g-values
		        for (var i = 0; i < 8; i++) {
		            var gx = X[i] + C[i];
	
		            // Construct high and low argument for squaring
		            var ga = gx & 0xffff;
		            var gb = gx >>> 16;
	
		            // Calculate high and low result of squaring
		            var gh = ((((ga * ga) >>> 17) + ga * gb) >>> 15) + gb * gb;
		            var gl = (((gx & 0xffff0000) * gx) | 0) + (((gx & 0x0000ffff) * gx) | 0);
	
		            // High XOR low
		            G[i] = gh ^ gl;
		        }
	
		        // Calculate new state values
		        X[0] = (G[0] + ((G[7] << 16) | (G[7] >>> 16)) + ((G[6] << 16) | (G[6] >>> 16))) | 0;
		        X[1] = (G[1] + ((G[0] << 8)  | (G[0] >>> 24)) + G[7]) | 0;
		        X[2] = (G[2] + ((G[1] << 16) | (G[1] >>> 16)) + ((G[0] << 16) | (G[0] >>> 16))) | 0;
		        X[3] = (G[3] + ((G[2] << 8)  | (G[2] >>> 24)) + G[1]) | 0;
		        X[4] = (G[4] + ((G[3] << 16) | (G[3] >>> 16)) + ((G[2] << 16) | (G[2] >>> 16))) | 0;
		        X[5] = (G[5] + ((G[4] << 8)  | (G[4] >>> 24)) + G[3]) | 0;
		        X[6] = (G[6] + ((G[5] << 16) | (G[5] >>> 16)) + ((G[4] << 16) | (G[4] >>> 16))) | 0;
		        X[7] = (G[7] + ((G[6] << 8)  | (G[6] >>> 24)) + G[5]) | 0;
		    }
	
		    /**
		     * Shortcut functions to the cipher's object interface.
		     *
		     * @example
		     *
		     *     var ciphertext = CryptoJS.RabbitLegacy.encrypt(message, key, cfg);
		     *     var plaintext  = CryptoJS.RabbitLegacy.decrypt(ciphertext, key, cfg);
		     */
		    C.RabbitLegacy = StreamCipher._createHelper(RabbitLegacy);
		}());
	
	
		return CryptoJS.RabbitLegacy;
	
	}));

/***/ }),
/* 135 */
/***/ (function(module, exports) {

	module.exports = {"SANDBOX":{"HTTP":"http://gw.api.tbsandbox.com/router/rest","HTTPS":"https://gw.api.tbsandbox.com/router/rest"},"PRODUCT":{"HTTP":"http://gw.api.taobao.com/router/rest","HTTPS":"https://eco.taobao.com/router/rest"},"GLOBAL":{"HTTP":"http://api.taobao.com/router/rest","HTTPS":"https://api.taobao.com/router/rest"}}

/***/ }),
/* 136 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var ClientFactory_1 = __webpack_require__(100);
	var HTTPClient = (function (_super) {
	    __extends(HTTPClient, _super);
	    function HTTPClient(cumsData) {
	        return _super.call(this, cumsData) || this;
	    }
	    HTTPClient.prototype.connect = function (url, ajaxType, isUpload, data) {
	        console.log("开启HTTP连接...数据处理中");
	        return _super.prototype.connect.call(this, url, ajaxType, isUpload, data);
	    };
	    return HTTPClient;
	}(ClientFactory_1.ClientFactory));
	exports.default = HTTPClient;


/***/ }),
/* 137 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	Object.defineProperty(exports, "__esModule", { value: true });
	var EventManager_1 = __webpack_require__(5);
	/**
	 * 属性面板
	 */
	var PropertyPanel = (function () {
	    function PropertyPanel() {
	        EventManager_1.default.register('selected', this.onSelected);
	    }
	    PropertyPanel.prototype.onSelected = function (args) {
	        //alert("selected")
	    };
	    return PropertyPanel;
	}());
	exports.default = PropertyPanel;


/***/ }),
/* 138 */
/***/ (function(module, exports) {

	module.exports = {"request":[{"path":"./","api":[{"id":"waybill.upload.image","http":"file/uploadFile.json"},{"id":"waybill.component.queryBizComponentList","http":"resource/queryBizComponentList.json"},{"id":"waybill.component.saveBizComponent","http":"component/saveBizComponent.json"},{"id":"waybill.component.publishBizComponent","http":"component/publishBizComponent.json"},{"id":"waybill.template.save","http":"template/saveTemplate.json"},{"id":"waybill.template.publish","http":"template/publishTemplate.json"},{"id":"waybill.customArea.save","http":"customArea/saveCustomArea.json"},{"id":"waybill.customAreaSingle.save","http":"customAreaSingle/saveCustomArea.json"},{"id":"waybill.customArea.publish","http":"customArea/publishCustomArea.json"},{"id":"waybill.customAreaSingle.publish","http":"customAreaSingle/publishCustomArea.json"},{"id":"waybill.cloundprint.getLeftNavInfoByUser","http":"editor/getLeftNavInfoByUser.json"},{"id":"waybill.cloundprint.getBizComponentByUser","http":"resource/getBizComponentByUser.json"},{"id":"waybill.cloundprint.updateResource","http":"resource/updateResource.json"},{"id":"waybill.print.publish","http":"resource/publish.json"},{"id":"waybill.print.getLeftNavInfo","http":"resource/getLeftNavInfo.json"},{"id":"waybill.print.userUpload","http":"user/upload.json"}]},{"path":"./anothertestapp/","api":[{"id":"waybill.cc.get","top":"cainiao.waybill.ii.get","http":"getbill/do"},{"id":"waybill.cn.set","top":"cainiao.waybill.ii.set"}]}]}

/***/ }),
/* 139 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	/**
	 * 插入图片命令
	 */
	var InsertBracodeCommand = (function (_super) {
	    __extends(InsertBracodeCommand, _super);
	    function InsertBracodeCommand(context) {
	        var _this = _super.call(this, context) || this;
	        _this.elements = [];
	        return _this;
	    }
	    InsertBracodeCommand.prototype.canUndo = function () {
	        return true;
	    };
	    InsertBracodeCommand.prototype.execute = function () {
	        var type = this.params.type;
	        if (!this.elements.length) {
	            this.elements = [this.context.editorPanel.insertBarcode(type)];
	            return;
	        }
	        this.context.editorPanel.insertBarcode(type);
	    };
	    InsertBracodeCommand.prototype.undo = function () {
	        var _this = this;
	        this.elements.forEach((function (element) {
	            _this.context.editorPanel.remove(element);
	        }).bind(this));
	    };
	    return InsertBracodeCommand;
	}(Command_1.default));
	CommandFactory_1.default.Instance.register('INSERT_BARCODE_COMMAND', function (action) {
	    var cmd = new InsertBracodeCommand(action.context);
	    cmd.params = action.data;
	    return cmd;
	});
	exports.default = InsertBracodeCommand;


/***/ }),
/* 140 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	/**
	 * 插入图片命令
	 */
	var InsertComponentCommand = (function (_super) {
	    __extends(InsertComponentCommand, _super);
	    function InsertComponentCommand(context) {
	        var _this = _super.call(this, context) || this;
	        _this.elements = [];
	        return _this;
	    }
	    InsertComponentCommand.prototype.canUndo = function () {
	        return true;
	    };
	    InsertComponentCommand.prototype.execute = function () {
	        if (!this.elements.length) {
	            this.elements = [this.context.editorPanel.insertComponent()];
	            return;
	        }
	        this.context.editorPanel.insertComponent();
	    };
	    InsertComponentCommand.prototype.undo = function () {
	        var _this = this;
	        this.elements.forEach((function (element) {
	            _this.context.editorPanel.remove(element);
	        }).bind(this));
	    };
	    return InsertComponentCommand;
	}(Command_1.default));
	CommandFactory_1.default.Instance.register('INSERT_COMPONENT_COMMAND', function (action) {
	    return new InsertComponentCommand(action.context);
	});
	exports.default = InsertComponentCommand;


/***/ }),
/* 141 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	/**
	 * 插入图片命令
	 */
	var InsertImageCommand = (function (_super) {
	    __extends(InsertImageCommand, _super);
	    function InsertImageCommand(context) {
	        var _this = _super.call(this, context) || this;
	        _this.elements = [];
	        return _this;
	    }
	    InsertImageCommand.prototype.canUndo = function () {
	        return true;
	    };
	    InsertImageCommand.prototype.execute = function () {
	        if (!this.elements.length) {
	            this.elements = [this.context.editorPanel.insertImage()];
	            return;
	        }
	        this.context.editorPanel.insertImage();
	    };
	    InsertImageCommand.prototype.undo = function () {
	        var _this = this;
	        this.elements.forEach((function (element) {
	            _this.context.editorPanel.remove(element);
	        }).bind(this));
	    };
	    return InsertImageCommand;
	}(Command_1.default));
	CommandFactory_1.default.Instance.register('INSERT_IMAGE_COMMAND', function (action) {
	    return new InsertImageCommand(action.context);
	});
	exports.default = InsertImageCommand;


/***/ }),
/* 142 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	/**
	 * 插入文本命令
	 */
	var InsertLineCommand = (function (_super) {
	    __extends(InsertLineCommand, _super);
	    function InsertLineCommand(context) {
	        var _this = _super.call(this, context) || this;
	        _this.elements = [];
	        return _this;
	    }
	    InsertLineCommand.prototype.canUndo = function () {
	        return true;
	    };
	    InsertLineCommand.prototype.execute = function () {
	        var _this = this;
	        var orientation = this.params.orientation;
	        if (!this.elements.length) {
	            this.elements = [this.context.editorPanel.insertLine(orientation)];
	            return;
	        }
	        this.elements.forEach((function (element) {
	            _this.context.editorPanel.add(element);
	        }).bind(this));
	    };
	    InsertLineCommand.prototype.undo = function () {
	        var _this = this;
	        this.elements.forEach((function (element) {
	            _this.context.editorPanel.remove(element);
	        }).bind(this));
	    };
	    return InsertLineCommand;
	}(Command_1.default));
	CommandFactory_1.default.Instance.register('INSERT_LINE_COMMAND', function (action) {
	    var cmd = new InsertLineCommand(action.context);
	    cmd.params = action.data;
	    return cmd;
	});
	exports.default = InsertLineCommand;


/***/ }),
/* 143 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	/**
	 * 插入图片命令
	 */
	var InsertQrcodeCommand = (function (_super) {
	    __extends(InsertQrcodeCommand, _super);
	    function InsertQrcodeCommand(context) {
	        var _this = _super.call(this, context) || this;
	        _this.elements = [];
	        return _this;
	    }
	    InsertQrcodeCommand.prototype.canUndo = function () {
	        return true;
	    };
	    InsertQrcodeCommand.prototype.execute = function () {
	        var type = this.params.type;
	        if (!this.elements.length) {
	            this.elements = [this.context.editorPanel.insertQrcode(type)];
	            return;
	        }
	        this.context.editorPanel.insertQrcode(type);
	    };
	    InsertQrcodeCommand.prototype.undo = function () {
	        var _this = this;
	        this.elements.forEach((function (element) {
	            _this.context.editorPanel.remove(element);
	        }).bind(this));
	    };
	    return InsertQrcodeCommand;
	}(Command_1.default));
	CommandFactory_1.default.Instance.register('INSERT_QRCODE_COMMAND', function (action) {
	    var cmd = new InsertQrcodeCommand(action.context);
	    cmd.params = action.data;
	    return cmd;
	});
	exports.default = InsertQrcodeCommand;


/***/ }),
/* 144 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	/**
	 * 插入文本命令
	 */
	var InsertRectCommand = (function (_super) {
	    __extends(InsertRectCommand, _super);
	    function InsertRectCommand(context) {
	        var _this = _super.call(this, context) || this;
	        _this.elements = [];
	        return _this;
	    }
	    InsertRectCommand.prototype.canUndo = function () {
	        return true;
	    };
	    InsertRectCommand.prototype.execute = function () {
	        var _this = this;
	        if (!this.elements.length) {
	            this.elements = [this.context.editorPanel.insertRect()];
	            return;
	        }
	        this.elements.forEach((function (element) {
	            _this.context.editorPanel.add(element);
	        }).bind(this));
	    };
	    InsertRectCommand.prototype.undo = function () {
	        var _this = this;
	        this.elements.forEach((function (element) {
	            _this.context.editorPanel.remove(element);
	        }).bind(this));
	    };
	    return InsertRectCommand;
	}(Command_1.default));
	CommandFactory_1.default.Instance.register('INSERT_RECT_COMMAND', function (action) {
	    var cmd = new InsertRectCommand(action.context);
	    return cmd;
	});
	exports.default = InsertRectCommand;


/***/ }),
/* 145 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var Index_1 = __webpack_require__(2);
	/**
	 * 插入文本命令
	 */
	var InsertTableColumnCommand = (function (_super) {
	    __extends(InsertTableColumnCommand, _super);
	    function InsertTableColumnCommand(context) {
	        var _this = _super.call(this, context) || this;
	        _this.elements = [];
	        return _this;
	    }
	    InsertTableColumnCommand.prototype.execute = function () {
	        var contextCell = this.context.editorPanel.actived[0];
	        var index = contextCell.index[1] + 1;
	        var table = contextCell.row.table;
	        // var cell = new TableCellDrawElement();
	        table.addColumn(index);
	        this.context.editorPanel.blur();
	        Index_1.EventManager.broadcast('DESINGNER_BLUR_EVENT', { event: { target: this } });
	        // this.context.editorPanel.select(cell);
	        // this.context.editorPanel.fireSelectedEvent(cell);
	    };
	    return InsertTableColumnCommand;
	}(Command_1.default));
	CommandFactory_1.default.Instance.register('INSERT_TABLE_COLUMN_COMMAND', function (action) {
	    var cmd = new InsertTableColumnCommand(action.context);
	    return cmd;
	});
	exports.default = InsertTableColumnCommand;


/***/ }),
/* 146 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var InsertTableCommand = (function (_super) {
	    __extends(InsertTableCommand, _super);
	    function InsertTableCommand(context) {
	        var _this = _super.call(this, context) || this;
	        _this.elements = [];
	        return _this;
	    }
	    InsertTableCommand.prototype.canUndo = function () {
	        return true;
	    };
	    InsertTableCommand.prototype.execute = function () {
	        var _this = this;
	        if (!this.elements.length) {
	            this.elements = [this.context.editorPanel.insertTable(this.rows, this.columns)];
	            return;
	        }
	        this.elements.forEach((function (element) {
	            _this.context.editorPanel.add(element);
	        }).bind(this));
	    };
	    InsertTableCommand.prototype.undo = function () {
	        var _this = this;
	        this.elements.forEach((function (element) {
	            _this.context.editorPanel.remove(element);
	        }).bind(this));
	    };
	    return InsertTableCommand;
	}(Command_1.default));
	CommandFactory_1.default.Instance.register('INSERT_TABLE_COMMAND', function (action) {
	    var cmd = new InsertTableCommand(action.context);
	    cmd.columns = action.data.columns;
	    cmd.rows = action.data.rows;
	    return cmd;
	});
	exports.default = InsertTableCommand;


/***/ }),
/* 147 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var Index_1 = __webpack_require__(26);
	var InsertTableRowCommand = (function (_super) {
	    __extends(InsertTableRowCommand, _super);
	    function InsertTableRowCommand(context) {
	        var _this = _super.call(this, context) || this;
	        _this.elements = [];
	        return _this;
	    }
	    InsertTableRowCommand.prototype.canUndo = function () {
	        return false;
	    };
	    InsertTableRowCommand.prototype.execute = function () {
	        var contextCell = this.context.editorPanel.actived[0];
	        var length = contextCell.row.cells.length;
	        var table = contextCell.row.table;
	        var index = contextCell.index[0] + 1;
	        var row = new Index_1.TableRowDrawElement();
	        row.index = index;
	        for (var j = 0; j < length; j++) {
	            var cell = new Index_1.TableCellDrawElement();
	            cell.index = [index, j];
	            row.addCell(cell);
	        }
	        table.addRow(row, index);
	    };
	    InsertTableRowCommand.prototype.undo = function () {
	        var _this = this;
	        this.elements.forEach((function (element) {
	            _this.context.editorPanel.remove(element);
	        }).bind(this));
	    };
	    return InsertTableRowCommand;
	}(Command_1.default));
	CommandFactory_1.default.Instance.register('INSERT_TABLE_ROW_COMMAND', function (action) {
	    var cmd = new InsertTableRowCommand(action.context);
	    return cmd;
	});
	exports.default = InsertTableRowCommand;


/***/ }),
/* 148 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	/**
	 * 插入文本命令
	 */
	var InsertTextCommand = (function (_super) {
	    __extends(InsertTextCommand, _super);
	    function InsertTextCommand(context) {
	        var _this = _super.call(this, context) || this;
	        _this.elements = [];
	        return _this;
	    }
	    InsertTextCommand.prototype.canUndo = function () {
	        return true;
	    };
	    InsertTextCommand.prototype.execute = function () {
	        var _this = this;
	        var orientation = this.params.orientation;
	        if (!this.elements.length) {
	            this.elements = [this.context.editorPanel.insertText(orientation)];
	            return;
	        }
	        this.elements.forEach((function (element) {
	            _this.context.editorPanel.add(element);
	        }).bind(this));
	    };
	    InsertTextCommand.prototype.undo = function () {
	        var _this = this;
	        this.elements.forEach((function (element) {
	            _this.context.editorPanel.remove(element);
	        }).bind(this));
	    };
	    return InsertTextCommand;
	}(Command_1.default));
	CommandFactory_1.default.Instance.register('INSERT_TEXT_COMMAND', function (action) {
	    var cmd = new InsertTextCommand(action.context);
	    cmd.params = action.data;
	    return cmd;
	});
	exports.default = InsertTextCommand;


/***/ }),
/* 149 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	/**
	 * 加载模版命令
	 */
	var LoadCommand = (function (_super) {
	    __extends(LoadCommand, _super);
	    function LoadCommand(context) {
	        return _super.call(this, context) || this;
	    }
	    LoadCommand.prototype.execute = function () {
	        console.log('LoadTemplateCommand');
	        this.context.editorPanel.load(this.template);
	    };
	    return LoadCommand;
	}(Command_1.default));
	CommandFactory_1.default.Instance.register('LOAD_COMMAND', function (action) {
	    var cmd = new LoadCommand(action.context);
	    cmd.template = action.data.template;
	    return cmd;
	});
	exports.default = LoadCommand;


/***/ }),
/* 150 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var EventManager_1 = __webpack_require__(5);
	var Server = __webpack_require__(98);
	/**
	 * 加载模版命令
	 */
	var LoadComponentCommand = (function (_super) {
	    __extends(LoadComponentCommand, _super);
	    function LoadComponentCommand(context) {
	        return _super.call(this, context) || this;
	    }
	    LoadComponentCommand.prototype.execute = function () {
	        console.log('LoadComponentCommand');
	        // this.context.editorPanel.loadComponent(null);
	        // return;
	        Server.get('waybill.component.queryBizComponentList', {
	            biz_component_id: this.id,
	            componentStatus: "2"
	        }).then(function (response) {
	            console.log("LoadComponentCommand:success");
	            if (response["success"]) {
	                EventManager_1.default.broadcast('DESINGER_COMPONENTITEM_EVENT', {
	                    event: { target: this },
	                    data: response["data"]
	                });
	            }
	        }, function (error) {
	            console.log("LoadComponentCommand:error::" + error);
	        });
	    };
	    return LoadComponentCommand;
	}(Command_1.default));
	CommandFactory_1.default.Instance.register('LOAD_COMPONENT_COMMAND', function (action) {
	    var cmd = new LoadComponentCommand(action.context);
	    cmd.id = action.data.id;
	    return cmd;
	});
	exports.default = LoadComponentCommand;


/***/ }),
/* 151 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var EventManager_1 = __webpack_require__(5);
	var Server = __webpack_require__(98);
	// let _ServiceConfig = require('!json!../config/serviceConfig.json');    //元素构造函数的配置信息
	/**
	 * 加载模版命令
	 */
	var LoadComponentListCommand = (function (_super) {
	    __extends(LoadComponentListCommand, _super);
	    function LoadComponentListCommand(context) {
	        var _this = _super.call(this, context) || this;
	        _this.list = [];
	        return _this;
	    }
	    LoadComponentListCommand.prototype.execute = function () {
	        // this.list = [1];
	        if (this.list.length > 0) {
	            EventManager_1.default.broadcast('DESINGER_COMPONENTLIST_EVENT', {
	                event: { target: this },
	                list: this.list
	            });
	            return;
	        }
	        // Server.init({serviceConfig: _ServiceConfig});
	        Server.get('waybill.cloundprint.getBizComponentByUser', {}).then(function (response) {
	            if (response["success"]) {
	                EventManager_1.default.broadcast('DESINGER_COMPONENTLIST_EVENT', {
	                    event: { target: this },
	                    list: response["data"]
	                });
	            }
	        }, function (error) {
	        });
	    };
	    return LoadComponentListCommand;
	}(Command_1.default));
	CommandFactory_1.default.Instance.register('LOAD_COMPONENTLIST_COMMAND', function (action) {
	    var cmd = new LoadComponentListCommand(action.context);
	    return cmd;
	});
	exports.default = LoadComponentListCommand;


/***/ }),
/* 152 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var Server = __webpack_require__(98);
	var Dispatcher_1 = __webpack_require__(4);
	/**
	 * 保存模版命令
	 */
	var LoadSideListCommand = (function (_super) {
	    __extends(LoadSideListCommand, _super);
	    function LoadSideListCommand(context) {
	        return _super.call(this, context) || this;
	    }
	    LoadSideListCommand.prototype.canUndo = function () {
	        return false;
	    };
	    LoadSideListCommand.prototype.execute = function () {
	        var resourceType = window["designerInitialProps"]["resourceType"];
	        var groupId = window["designerInitialProps"]["groupId"];
	        var currentId = window["designerInitialProps"]["templateId"] || window["designerInitialProps"]["bizComponentId"];
	        var params = {
	            resourceType: resourceType,
	            groupId: groupId,
	            currentId: currentId
	        };
	        Server.get("waybill.print.getLeftNavInfo", params).then(function (response) {
	            if (response["success"] && response["success"] == true) {
	                Dispatcher_1.EventManager.broadcast('LOAD_TEMPLATE_LIST_EVENT', { list: response["data"] });
	            }
	        }, function (error) {
	            //console.log(error)
	        });
	    };
	    return LoadSideListCommand;
	}(Command_1.default));
	//注册命令到系统中
	CommandFactory_1.default.Instance.register('LOAD_SIDEBAR_COMMAND', function (action) {
	    var cmd = new LoadSideListCommand(action.context);
	    cmd.resourceType = action.data.resourceType;
	    cmd.templateType = action.data.templateType;
	    return cmd;
	});
	//导出模块
	exports.default = LoadSideListCommand;


/***/ }),
/* 153 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var Server = __webpack_require__(98);
	var Dispatcher_1 = __webpack_require__(4);
	/**
	 * 保存模版命令
	 */
	var LoadTemplateListCommand = (function (_super) {
	    __extends(LoadTemplateListCommand, _super);
	    function LoadTemplateListCommand(context) {
	        return _super.call(this, context) || this;
	    }
	    LoadTemplateListCommand.prototype.canUndo = function () {
	        return false;
	    };
	    LoadTemplateListCommand.prototype.execute = function () {
	        var resource_type = window["designerInitialProps"]["resourceType"];
	        var params = {
	            resource_type: this.resourceType,
	        };
	        if (this.templateType) {
	            params = {
	                resource_type: this.resourceType,
	                template_type: this.templateType
	            };
	        }
	        console.log('waybill.cloundprint.getLeftNavInfoByUser::call');
	        Server.get("waybill.cloundprint.getLeftNavInfoByUser", params).then(function (response) {
	            if (response["success"] && response["success"] == true) {
	                console.log('waybill.cloundprint.getLeftNavInfoByUser::success');
	                Dispatcher_1.EventManager.broadcast('LOAD_TEMPLATE_LIST_EVENT', { list: response["data"] });
	            }
	        }, function (error) {
	            //console.log(error)
	        });
	    };
	    return LoadTemplateListCommand;
	}(Command_1.default));
	//注册命令到系统中
	CommandFactory_1.default.Instance.register('LOAD_TEMPLATE_LIST_COMMAND', function (action) {
	    var cmd = new LoadTemplateListCommand(action.context);
	    cmd.resourceType = action.data.resourceType;
	    cmd.templateType = action.data.templateType;
	    return cmd;
	});
	//导出模块
	exports.default = LoadTemplateListCommand;


/***/ }),
/* 154 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	/**
	 * 删除元素命令
	 */
	var ModifyTextScriptCommand = (function (_super) {
	    __extends(ModifyTextScriptCommand, _super);
	    function ModifyTextScriptCommand(context) {
	        return _super.call(this, context) || this;
	    }
	    ModifyTextScriptCommand.prototype.execute = function () {
	        var _this = this;
	        this.context.editorPanel.actived.forEach(function (item) {
	            // var split = this.split == '\r\n' ? '\\r\\n' : this.split;
	            var split = _this.split;
	            var text = "<% for(var i=0; i<" + _this.dataset + ".length; ++i) { %>" + _this.text.join('-') + (_this.split == '\r\n' ? '<%="\\n"%>' : split) + "<%}%>";
	            var aliasName = _this.aliasName.join('-') + (_this.split == '\r\n' ? " ↵" : _this.split);
	            item.data.checked = _this.text;
	            item.data.split = _this.split;
	            item.update([
	                {
	                    "propertyName": "aliasName",
	                    "propertyValue": aliasName,
	                },
	                {
	                    "propertyName": "text",
	                    "propertyValue": text,
	                }
	            ]);
	        });
	    };
	    return ModifyTextScriptCommand;
	}(Command_1.default));
	//注册命令到系统中
	CommandFactory_1.default.Instance.register('MODIFY_TEXT_SCRIPT_COMMAND', function (action) {
	    var cmd = new ModifyTextScriptCommand(action.context);
	    cmd.text = action.data.value;
	    cmd.aliasName = action.data.aliasName;
	    cmd.dataset = action.data.dataset;
	    cmd.split = action.data.split;
	    return cmd;
	});
	//导出模块
	exports.default = ModifyTextScriptCommand;


/***/ }),
/* 155 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var Clipboard_1 = __webpack_require__(11);
	var PasteCommand = (function (_super) {
	    __extends(PasteCommand, _super);
	    function PasteCommand(context) {
	        var _this = _super.call(this, context) || this;
	        _this.elements = [];
	        _this.clipboard = new Clipboard_1.Clipboard(context);
	        return _this;
	    }
	    PasteCommand.prototype.canUndo = function () {
	        return true;
	    };
	    PasteCommand.prototype.execute = function () {
	        var _this = this;
	        //console.log('PasteCommand')
	        if (this.elements.length < 1) {
	            this.elements = this.clipboard.paste();
	            return;
	        }
	        this.elements.forEach((function (element) {
	            _this.context.editorPanel.add(element);
	        }).bind(this));
	    };
	    PasteCommand.prototype.undo = function () {
	        var _this = this;
	        this.elements.forEach((function (element) {
	            _this.context.editorPanel.remove(element);
	        }).bind(this));
	    };
	    return PasteCommand;
	}(Command_1.default));
	//注册命令到系统中
	CommandFactory_1.default.Instance.register('PASTE_COMMAND', function (action) {
	    return new PasteCommand(action.context);
	});
	//导出模块
	exports.default = PasteCommand;


/***/ }),
/* 156 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var zoomConfig = __webpack_require__(157); //缩放比例配置
	/**
	 * 预览操作，弹出一个窗口
	 */
	var PreviewCommand = (function (_super) {
	    __extends(PreviewCommand, _super);
	    function PreviewCommand(context) {
	        return _super.call(this, context) || this;
	    }
	    PreviewCommand.prototype.execute = function () {
	        document.getElementById('J_previewPanel').style.display = 'block';
	    };
	    return PreviewCommand;
	}(Command_1.default));
	//注册命令到系统中
	CommandFactory_1.default.Instance.register('PREVIEW_COMMAND', function (action) {
	    var cmd = new PreviewCommand(action.context);
	    cmd.params = action.data;
	    return cmd;
	});
	//导出模块
	exports.default = PreviewCommand;


/***/ }),
/* 157 */
/***/ (function(module, exports) {

	module.exports = {"scaleThanOne":1,"scaleLessOne":0.25}

/***/ }),
/* 158 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	/**
	 * 删除元素命令
	 */
	var PropertyChangeCommand = (function (_super) {
	    __extends(PropertyChangeCommand, _super);
	    //继承Command扩展功能
	    function PropertyChangeCommand(context) {
	        return _super.call(this, context) || this;
	    }
	    PropertyChangeCommand.prototype.execute = function () {
	        var _this = this;
	        //这里实现删除业务逻辑
	        var propertyName = this.propertyName;
	        var propertyValue = this.propertyValue;
	        var suffix = propertyValue + '';
	        if (this.properties) {
	            var properties_1 = this.properties;
	            this.context.editorPanel.actived.forEach(function (item) {
	                item.update(properties_1);
	            });
	            return;
	        }
	        if (this.move) {
	            if (this.context.editorPanel.editing) {
	                return;
	            }
	            this.context.editorPanel.actived.forEach(function (item) {
	                propertyValue = item[propertyName];
	                if (_this.increment) {
	                    propertyValue = ++propertyValue;
	                }
	                if (_this.decrement) {
	                    propertyValue = --propertyValue;
	                }
	                item.update([{
	                        propertyName: propertyName,
	                        propertyValue: propertyValue
	                    }]);
	            });
	            return;
	        }
	        suffix = suffix.replace(String(parseFloat(propertyValue)), '');
	        if (this.increment) {
	            propertyValue = parseFloat(propertyValue) || 0;
	            propertyValue = (propertyValue / this.rate + 1) * this.rate;
	            propertyValue = Math.round(propertyValue * 100) / 100;
	            if (suffix) {
	                propertyValue = propertyValue + suffix;
	            }
	        }
	        else if (this.decrement) {
	            propertyValue = parseFloat(propertyValue) || 0;
	            propertyValue = (propertyValue / this.rate - 1) * this.rate;
	            propertyValue = Math.round(propertyValue * 100) / 100;
	            if (suffix) {
	                propertyValue = propertyValue + suffix;
	            }
	        }
	        this.context.editorPanel.actived.forEach(function (item) {
	            item.update([{
	                    propertyName: propertyName,
	                    propertyValue: propertyValue
	                }]);
	        });
	    };
	    return PropertyChangeCommand;
	}(Command_1.default));
	//注册命令到系统中
	CommandFactory_1.default.Instance.register('PROPERTY_CHANGE_COMMAND', function (action) {
	    var cmd = new PropertyChangeCommand(action.context);
	    cmd.propertyName = action.data.propertyName;
	    cmd.propertyValue = action.data.propertyValue;
	    cmd.increment = action.data.increment;
	    cmd.decrement = action.data.decrement;
	    cmd.properties = action.data.properties;
	    cmd.rate = action.data.rate;
	    cmd.move = action.data.move;
	    return cmd;
	});
	//导出模块
	exports.default = PropertyChangeCommand;


/***/ }),
/* 159 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var Server = __webpack_require__(98);
	var Dispatcher_1 = __webpack_require__(4);
	/**
	 * 保存模版命令
	 */
	var PublishCommand = (function (_super) {
	    __extends(PublishCommand, _super);
	    function PublishCommand(context) {
	        return _super.call(this, context) || this;
	    }
	    PublishCommand.prototype.canUndo = function () {
	        return false;
	    };
	    PublishCommand.prototype.execute = function () {
	        // var template = this.context.editorPanel.getTemplate();
	        var resource_type = window["designerInitialProps"]["resourceType"];
	        var ajaxId, ajaxParams, resource_type_text;
	        switch (resource_type) {
	            case "1":
	                //模板
	                resource_type_text = '模板';
	                ajaxId = 'waybill.template.publish';
	                ajaxParams = {
	                    template_id: window["designerInitialProps"]["templateId"],
	                    template_type: window["designerInitialProps"]["templateType"]
	                };
	                break;
	            case "2":
	                //自定义区
	                var appKey = window["designerInitialProps"]["appKey"];
	                resource_type_text = '面单';
	                if (appKey) {
	                    ajaxId = 'waybill.customAreaSingle.publish';
	                    ajaxParams = {
	                        custom_area_id: window["designerInitialProps"]["customAreaId"]
	                    };
	                }
	                else {
	                    ajaxId = 'waybill.customArea.publish';
	                    ajaxParams = {
	                        template_id: window["designerInitialProps"]["templateId"],
	                        customAreaId: window["designerInitialProps"]["customAreaId"]
	                    };
	                }
	                break;
	            case "3":
	                //组件
	                resource_type_text = '组件';
	                ajaxId = 'waybill.component.publishBizComponent';
	                ajaxParams = {
	                    biz_component_id: window["designerInitialProps"]["bizComponentId"]
	                };
	                break;
	            default:
	                break;
	        }
	        Server.get(ajaxId, ajaxParams).then(function (response) {
	            if (response.hasOwnProperty("success") && response["success"] == true) {
	                var args = {
	                    msg: resource_type_text + "发布成功",
	                    type: "success"
	                };
	                Dispatcher_1.EventManager.broadcast('NEW_TOAST_EVENT', args);
	                console.log(resource_type_text + "发布成功");
	            }
	            else {
	                var args = {
	                    msg: "未知原因，" + resource_type_text + "发布失败",
	                    type: "error"
	                };
	                if (response.hasOwnProperty("success") && response["success"].hasOwnProperty("errorMsg")) {
	                    args.msg = response["errorMsg"];
	                }
	                Dispatcher_1.EventManager.broadcast('NEW_TOAST_EVENT', args);
	            }
	        });
	    };
	    return PublishCommand;
	}(Command_1.default));
	//注册命令到系统中
	CommandFactory_1.default.Instance.register('PUBLISH_COMMAND', function (action) {
	    return new PublishCommand(action.context);
	});
	//导出模块
	exports.default = PublishCommand;


/***/ }),
/* 160 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var Server = __webpack_require__(98);
	var Dispatcher_1 = __webpack_require__(4);
	/**
	 * 保存模版命令
	 */
	var ResourcePublishCommand = (function (_super) {
	    __extends(ResourcePublishCommand, _super);
	    function ResourcePublishCommand(context) {
	        return _super.call(this, context) || this;
	    }
	    ResourcePublishCommand.prototype.canUndo = function () {
	        return false;
	    };
	    ResourcePublishCommand.prototype.execute = function () {
	        // var template = this.context.editorPanel.getTemplate();
	        var resource_type = window["designerInitialProps"]["resourceType"];
	        var template_type = window["designerInitialProps"]["templateType"];
	        var ajaxId, ajaxParams, resource_type_text;
	        resource_type_text = '模板';
	        ajaxId = 'waybill.print.publish';
	        ajaxParams = {
	            resourceId: window["designerInitialProps"]["templateId"] || window["designerInitialProps"]["bizComponentId"]
	        };
	        Server.get(ajaxId, ajaxParams).then(function (response) {
	            if (response.hasOwnProperty("success") && response["success"] == true) {
	                var args = {
	                    msg: resource_type_text + "发布成功",
	                    type: "success"
	                };
	                Dispatcher_1.EventManager.broadcast('NEW_TOAST_EVENT', args);
	                //发布模板成功之后，更新左侧模板列表的状态
	                if (resource_type) {
	                    Dispatcher_1.Dispatcher.dispatch({
	                        type: 'LOAD_SIDEBAR_COMMAND',
	                        data: {
	                            resourceType: resource_type,
	                            templateType: template_type
	                        }
	                    });
	                }
	            }
	            else {
	                var args = {
	                    msg: "未知原因，" + resource_type_text + "发布失败",
	                    type: "error"
	                };
	                if (response.hasOwnProperty("success") && response.hasOwnProperty("errorMsg")) {
	                    args.msg = response["errorMsg"];
	                }
	                Dispatcher_1.EventManager.broadcast('NEW_TOAST_EVENT', args);
	            }
	        });
	    };
	    return ResourcePublishCommand;
	}(Command_1.default));
	//注册命令到系统中
	CommandFactory_1.default.Instance.register('RESOURCES_PUBLISH_COMMAND', function (action) {
	    return new ResourcePublishCommand(action.context);
	});
	//导出模块
	exports.default = ResourcePublishCommand;


/***/ }),
/* 161 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var Server = __webpack_require__(98);
	var Dispatcher_1 = __webpack_require__(4);
	var TemplateParser_1 = __webpack_require__(93);
	/**
	 * 保存模版命令
	 */
	var SaveCommand = (function (_super) {
	    __extends(SaveCommand, _super);
	    function SaveCommand(context) {
	        return _super.call(this, context) || this;
	    }
	    SaveCommand.prototype.canUndo = function () {
	        return false;
	    };
	    SaveCommand.prototype.execute = function () {
	        var template;
	        if (this.context.editMode != this.editMode) {
	            return;
	        }
	        if (this.context.editMode == 0) {
	            //源码编辑模式
	            template = this.code;
	            try {
	                var parser = new TemplateParser_1.default();
	                parser.parse(template);
	            }
	            catch (error) {
	                var args = {
	                    msg: "存在语法错误，请检查",
	                    type: "error"
	                };
	                Dispatcher_1.EventManager.broadcast('NEW_TOAST_EVENT', args);
	            }
	        }
	        else {
	            //视图编辑模式
	            template = this.context.editorPanel.getTemplate();
	        }
	        var resource_type = window["designerInitialProps"]["resourceType"];
	        var ajaxId, ajaxParams;
	        var preview_data = window.template_preview_data ? JSON.stringify(window.template_preview_data) : "";
	        template = encodeURIComponent(template);
	        switch (resource_type) {
	            case "1":
	                //模板
	                ajaxId = 'waybill.template.save';
	                ajaxParams = {
	                    template_id: window["designerInitialProps"]["templateId"],
	                    template_name: window["designerInitialProps"]["templateName"],
	                    template_content: template,
	                    template_type: window["designerInitialProps"]["templateType"],
	                    cp_code: window["designerInitialProps"]["cpCode"],
	                    preview_data: preview_data
	                };
	                break;
	            case "2":
	                //自定义区
	                var appKey = window["designerInitialProps"]["appKey"];
	                if (appKey) {
	                    ajaxId = 'waybill.customAreaSingle.save';
	                    ajaxParams = {
	                        custom_area_Id: window["designerInitialProps"]["customAreaId"],
	                        app_key: appKey,
	                        custom_area_content: template
	                    };
	                }
	                else {
	                    ajaxId = 'waybill.customArea.save';
	                    ajaxParams = {
	                        template_id: window["designerInitialProps"]["templateId"],
	                        custom_area_Id: window["designerInitialProps"]["customAreaId"],
	                        custom_area_name: window["designerInitialProps"]["customAreaName"],
	                        custom_area_content: template,
	                        preview_data: preview_data
	                    };
	                }
	                break;
	            case "3":
	                //组件
	                ajaxId = 'waybill.component.saveBizComponent';
	                ajaxParams = {
	                    biz_component_id: window["designerInitialProps"]["bizComponentId"],
	                    biz_component_name: window["designerInitialProps"]["bizComponentName"],
	                    biz_component_content: template,
	                    preview_data: preview_data
	                };
	                break;
	            default:
	                break;
	        }
	        Server.post(ajaxId, ajaxParams).then(function (response) {
	            if (response.hasOwnProperty("success") && response["success"] == true) {
	                var args = {
	                    msg: designer_lang.template_save_success,
	                    type: "success"
	                };
	                Dispatcher_1.EventManager.broadcast('NEW_TOAST_EVENT', args);
	                console.log("模板保存成功");
	            }
	            else {
	                var args = {
	                    msg: "未知原因，模板保存失败",
	                    type: "error"
	                };
	                if (response.hasOwnProperty("success") && response["success"].hasOwnProperty("errorMsg")) {
	                    args.msg = response["errorMsg"];
	                }
	                Dispatcher_1.EventManager.broadcast('NEW_TOAST_EVENT', args);
	            }
	        });
	    };
	    return SaveCommand;
	}(Command_1.default));
	//注册命令到系统中
	CommandFactory_1.default.Instance.register('SAVE_COMMAND', function (action) {
	    var cmd = new SaveCommand(action.context);
	    cmd.editMode = action.data.editMode;
	    cmd.code = action.data.code;
	    return cmd;
	});
	//导出模块
	exports.default = SaveCommand;


/***/ }),
/* 162 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var EventManager_1 = __webpack_require__(5);
	var Enum_1 = __webpack_require__(92);
	/**
	 * 保存模版命令
	 */
	var ToggleEditModeCommand = (function (_super) {
	    __extends(ToggleEditModeCommand, _super);
	    function ToggleEditModeCommand(context) {
	        return _super.call(this, context) || this;
	    }
	    ToggleEditModeCommand.prototype.canUndo = function () {
	        return false;
	    };
	    ToggleEditModeCommand.prototype.execute = function () {
	        this.context.editMode = this.editMode;
	        if (this.editMode == Enum_1.EditMode.SourceCode) {
	            var code = this.context.editorPanel.getTemplate();
	            EventManager_1.default.broadcast('SOURCECODE_SHOW_EVENT', { event: { target: this }, code: code, editMode: this.editMode });
	        }
	        else {
	            var code = this.code;
	            this.context.editorPanel.load(code);
	            EventManager_1.default.broadcast('DESINGER_SHOW_EVENT', { event: { target: this }, editMode: this.editMode });
	        }
	        this.context.editorPanel.fireBlurEvent();
	    };
	    return ToggleEditModeCommand;
	}(Command_1.default));
	//注册命令到系统中
	CommandFactory_1.default.Instance.register('TOGGLE_EDITMODE_COMMAND', function (action) {
	    var cmd = new ToggleEditModeCommand(action.context);
	    cmd.editMode = action.data.editMode;
	    cmd.code = action.data.code;
	    return cmd;
	});
	//导出模块
	exports.default = ToggleEditModeCommand;


/***/ }),
/* 163 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var Server = __webpack_require__(98);
	var Dispatcher_1 = __webpack_require__(4);
	var TemplateParser_1 = __webpack_require__(93);
	/**
	 * 保存模版命令
	 */
	var UpdateResouceCommand = (function (_super) {
	    __extends(UpdateResouceCommand, _super);
	    function UpdateResouceCommand(context) {
	        return _super.call(this, context) || this;
	    }
	    UpdateResouceCommand.prototype.canUndo = function () {
	        return false;
	    };
	    UpdateResouceCommand.prototype.execute = function () {
	        var template;
	        if (this.context.editMode != this.editMode) {
	            return;
	        }
	        if (this.context.editMode == 0) {
	            //源码编辑模式
	            template = this.code;
	            try {
	                var parser = new TemplateParser_1.default();
	                parser.parse(template);
	            }
	            catch (error) {
	                var args = {
	                    msg: "存在语法错误，请检查",
	                    type: "error"
	                };
	                Dispatcher_1.EventManager.broadcast('NEW_TOAST_EVENT', args);
	            }
	        }
	        else {
	            //视图编辑模式
	            template = this.context.editorPanel.getTemplate();
	        }
	        var resource_type = window["designerInitialProps"]["resourceType"];
	        var template_type = window["designerInitialProps"]["templateType"];
	        var ajaxId, ajaxParams;
	        var preview_data = window.template_preview_data ? JSON.stringify(window.template_preview_data) : "";
	        template = encodeURIComponent(template);
	        //模板
	        ajaxId = 'waybill.cloundprint.updateResource';
	        ajaxParams = {
	            resourceId: window["designerInitialProps"]["templateId"] || window["designerInitialProps"]["bizComponentId"],
	            groupId: window["designerInitialProps"]["groupId"],
	            content: template,
	            preview_data: preview_data
	        };
	        Server.post(ajaxId, ajaxParams).then(function (response) {
	            if (response.hasOwnProperty("success") && response["success"] == true) {
	                var args = {
	                    msg: designer_lang.template_save_success,
	                    type: "success"
	                };
	                Dispatcher_1.EventManager.broadcast('NEW_TOAST_EVENT', args);
	                //保存模板成功之后，更新左侧模板列表的状态
	                if (resource_type) {
	                    Dispatcher_1.Dispatcher.dispatch({
	                        type: 'LOAD_SIDEBAR_COMMAND',
	                        data: {
	                            resourceType: resource_type,
	                            templateType: template_type
	                        }
	                    });
	                }
	            }
	            else {
	                var args = {
	                    msg: "未知原因，模板保存失败",
	                    type: "error"
	                };
	                if (response.hasOwnProperty("success") && response.hasOwnProperty("errorMsg")) {
	                    args.msg = response["errorMsg"];
	                }
	                Dispatcher_1.EventManager.broadcast('NEW_TOAST_EVENT', args);
	            }
	        });
	    };
	    return UpdateResouceCommand;
	}(Command_1.default));
	//注册命令到系统中
	CommandFactory_1.default.Instance.register('UPDATE_RESOURCE_COMMAND', function (action) {
	    var cmd = new UpdateResouceCommand(action.context);
	    cmd.editMode = action.data.editMode;
	    cmd.code = action.data.code;
	    return cmd;
	});
	//导出模块
	exports.default = UpdateResouceCommand;


/***/ }),
/* 164 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var Index_1 = __webpack_require__(26);
	/**
	 * 元素排列方式枚举
	 */
	var Arrange;
	(function (Arrange) {
	    Arrange[Arrange["Front"] = 1] = "Front";
	    Arrange[Arrange["Back"] = 2] = "Back";
	})(Arrange || (Arrange = {}));
	/**
	 * 设置元素的ZIndex:置顶，置低，上一层，下一层
	 */
	var ZIndexCommand = (function (_super) {
	    __extends(ZIndexCommand, _super);
	    function ZIndexCommand(context) {
	        var _this = _super.call(this, context) || this;
	        _this.sorted = [];
	        return _this;
	    }
	    ZIndexCommand.prototype.canUndo = function () {
	        return false;
	    };
	    //重置zIndex
	    ZIndexCommand.prototype.reset = function () {
	        this.sorted.forEach(function (elem, i) {
	            if (elem.forElement) {
	                elem.forElement.render();
	            }
	            if (elem instanceof Index_1.LineDrawElement) {
	                elem.render();
	            }
	        });
	    };
	    //置顶
	    ZIndexCommand.prototype.bringToFront = function () {
	        var _loop_1 = function () {
	            var layout = current.parent;
	            var parent_1 = layout && layout.parent;
	            if (!parent_1) {
	                return "continue";
	            }
	            var children = parent_1.children;
	            children.map(function (item, index) {
	                if (typeof item.zIndex === 'undefined') {
	                    item.zIndex = index;
	                }
	            });
	            maxIndex = children[0].zIndex;
	            children.forEach(function (item, index) {
	                if (maxIndex < item.zIndex) {
	                    maxIndex = item.zIndex;
	                }
	            });
	            children.some(function (elem, index, sorted) {
	                if (elem.id == layout.id) {
	                    elem.zIndex = maxIndex + 1;
	                    return true;
	                }
	                return false;
	            });
	            sorted = [];
	            children.forEach(function (item, index) {
	                sorted.push(item);
	            });
	            sorted.sort(function (elem, next) {
	                return elem.zIndex - next.zIndex;
	            });
	            sorted.forEach(function (elem, i) {
	                if (elem.forElement) {
	                    elem.forElement.zIndex = i + 1;
	                }
	                if (elem instanceof Index_1.LineDrawElement) {
	                    elem.zIndex = i + 1;
	                }
	            });
	            this_1.sorted = children;
	            this_1.reset();
	        };
	        var this_1 = this, maxIndex, sorted;
	        for (var _i = 0, _a = this.context.editorPanel.actived; _i < _a.length; _i++) {
	            var current = _a[_i];
	            _loop_1();
	        }
	    };
	    //置底
	    ZIndexCommand.prototype.bringToBack = function () {
	        var _loop_2 = function () {
	            var layout = current.parent;
	            var parent_2 = layout && layout.parent;
	            if (!parent_2) {
	                return "continue";
	            }
	            var children = parent_2.children;
	            children.map(function (item, index) {
	                if (typeof item.zIndex === 'undefined') {
	                    item.zIndex = index;
	                }
	            });
	            minIndex = children[0].zIndex;
	            children.forEach(function (item, index) {
	                if (minIndex > item.zIndex) {
	                    minIndex = item.zIndex;
	                }
	            });
	            children.some(function (elem, index, sorted) {
	                if (elem.id == layout.id) {
	                    elem.zIndex = minIndex - 1;
	                    return true;
	                }
	                return false;
	            });
	            sorted = [];
	            children.forEach(function (item, index) {
	                sorted.push(item);
	            });
	            sorted.sort(function (elem, next) {
	                return elem.zIndex - next.zIndex;
	            });
	            sorted.forEach(function (elem, i) {
	                if (elem.forElement) {
	                    elem.forElement.zIndex = i + 1;
	                }
	                if (elem instanceof Index_1.LineDrawElement) {
	                    elem.zIndex = i + 1;
	                }
	            });
	            this_2.sorted = children;
	            this_2.reset();
	        };
	        var this_2 = this, minIndex, sorted;
	        for (var _i = 0, _a = this.context.editorPanel.actived; _i < _a.length; _i++) {
	            var current = _a[_i];
	            _loop_2();
	        }
	    };
	    ZIndexCommand.prototype.execute = function () {
	        //置底
	        if (this.arrange == Arrange.Back) {
	            this.bringToBack();
	            return;
	        }
	        //置顶
	        if (this.arrange == Arrange.Front) {
	            this.bringToFront();
	            return;
	        }
	    };
	    return ZIndexCommand;
	}(Command_1.default));
	//注册命令到系统中
	CommandFactory_1.default.Instance.register('ZINDEX_COMMAND', function (action) {
	    var cmd = new ZIndexCommand(action.context);
	    cmd.arrange = action.data.arrange;
	    return cmd;
	});
	//导出模块
	exports.default = ZIndexCommand;


/***/ }),
/* 165 */
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	var __extends = (this && this.__extends) || (function () {
	    var extendStatics = Object.setPrototypeOf ||
	        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
	        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
	    return function (d, b) {
	        extendStatics(d, b);
	        function __() { this.constructor = d; }
	        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
	    };
	})();
	Object.defineProperty(exports, "__esModule", { value: true });
	var CommandFactory_1 = __webpack_require__(3);
	var Command_1 = __webpack_require__(6);
	var Index_1 = __webpack_require__(16);
	var zoomConfig = __webpack_require__(157); //缩放比例配置
	/**
	 * 缩放命令
	 */
	var ZoomCommand = (function (_super) {
	    __extends(ZoomCommand, _super);
	    function ZoomCommand(context) {
	        var _this = _super.call(this, context) || this;
	        _this.elments = [];
	        return _this;
	    }
	    ZoomCommand.prototype.canUndo = function () {
	        return false;
	    };
	    ZoomCommand.prototype.adjustScale = function () {
	        var flag = this.params.flag;
	        var scale = Index_1.Unit.scale;
	        //放大
	        if (flag === "+") {
	            if (scale < 1) {
	                scale = scale + zoomConfig.scaleLessOne;
	            }
	            else {
	                scale = scale + zoomConfig.scaleThanOne;
	            }
	        }
	        else if (flag === '-') {
	            if (scale <= 1) {
	                scale = scale - zoomConfig.scaleLessOne;
	            }
	            else {
	                scale = scale - zoomConfig.scaleThanOne;
	            }
	        }
	        Index_1.Unit.scale = scale;
	    };
	    ZoomCommand.prototype.execute = function () {
	        //调整缩放比例
	        this.adjustScale();
	        this.context.editorPanel.zoom();
	    };
	    return ZoomCommand;
	}(Command_1.default));
	//注册命令到系统中
	CommandFactory_1.default.Instance.register('ZOOM_COMMAND', function (action) {
	    var cmd = new ZoomCommand(action.context);
	    cmd.params = action.data;
	    return cmd;
	});
	//导出模块
	exports.default = ZoomCommand;


/***/ })
/******/ ])
});
;
//# sourceMappingURL=CNPrintDesigner.js.map